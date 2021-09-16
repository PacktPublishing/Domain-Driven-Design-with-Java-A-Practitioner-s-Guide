package com.premonition.lc.ch08.domain;

import com.premonition.lc.ch08.domain.commands.*;
import com.premonition.lc.ch08.domain.events.*;
import com.premonition.lc.ch08.domain.exceptions.LCAmountMissingException;
import com.premonition.lc.ch08.domain.exceptions.LCApplicationAlreadySubmittedException;
import org.axonframework.messaging.interceptors.BeanValidationInterceptor;
import org.axonframework.messaging.interceptors.JSR303ViolationException;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Set;

import static com.premonition.lc.ch08.domain.commands.StartNewLCApplicationCommand.startApplication;
import static javax.money.Monetary.getCurrency;
import static org.axonframework.test.matchers.Matchers.andNoMore;
import static org.axonframework.test.matchers.Matchers.exactSequenceOf;
import static org.axonframework.test.matchers.Matchers.messageWithPayload;
import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LCApplicationAggregateTests {

    public static final Money FIVE_THOUSAND_DOLLARS = Money.of(5000, getCurrency("USD"));
    private static final Money THOUSAND_DOLLARS = Money.of(1000, getCurrency("USD"));
    private FixtureConfiguration<LCApplication> fixture;

    private static Item item() {
        return Item
                .builder()
                .productId(ProductId.randomId())
                .quantity(2)
                .build();
    }

    private static Merchandise merchandise() {
        return merchandise(Set.of(item(), item()));
    }

    private static Merchandise merchandise(Set<Item> items) {
        return Merchandise
                .builder()
                .items(items)
                .build();
    }

    private static ChangeMerchandiseCommand changeMerchandise(LCApplicationId id, Merchandise merchandise) {
        return ChangeMerchandiseCommand
                .builder()
                .id(id)
                .merchandise(merchandise)
                .build();
    }

    @BeforeEach
    void setUp() {
        fixture = new AggregateTestFixture<>(LCApplication.class);
        fixture.registerCommandDispatchInterceptor(new BeanValidationInterceptor<>());
    }

    @Test
    void shouldPublishLCApplicationIsStarted() {
        fixture.given()

                .when(startApplication(ApplicantId.randomId(), "My Test"))

                .expectEventsMatching(exactSequenceOf(
                        messageWithPayload(any(LCApplicationStartedEvent.class)),
                        andNoMore()
                ));
    }

    @Test
    void shouldAllowAmountChangeIfLCIsNotSubmitted() {
        final LCApplicationId id = LCApplicationId.randomId();
        final Money lcAmount = THOUSAND_DOLLARS;
        fixture.given(new LCApplicationStartedEvent(id, ApplicantId.randomId(),
                        "My LC", LCState.DRAFT))
                .when(new ChangeLCAmountCommand(id, lcAmount))
                .expectEvents(new LCAmountChangedEvent(id, lcAmount));
    }

    @Test
    void shouldNotAllowAmountChangeToNegativeAmount() {
        final LCApplicationId id = LCApplicationId.randomId();
        final Money lcAmount = Money.of(-100, getCurrency("USD"));
        assertThrows(JSR303ViolationException.class, () ->
                fixture.given(new LCApplicationStartedEvent(id, ApplicantId.randomId(),
                                "My LC", LCState.DRAFT))
                        .when(new ChangeLCAmountCommand(id, lcAmount))
        );
    }

    @Test
    void shouldAllowAmountChangeMoreThanOnceIfLCIsNotSubmitted() {
        final LCApplicationId id = LCApplicationId.randomId();
        final Money lcAmount = THOUSAND_DOLLARS;
        fixture.given(new LCApplicationStartedEvent(id, ApplicantId.randomId(),
                                "My LC", LCState.DRAFT),
                        new LCAmountChangedEvent(id, FIVE_THOUSAND_DOLLARS))
                .when(new ChangeLCAmountCommand(id, lcAmount))
                .expectEvents(new LCAmountChangedEvent(id, lcAmount));
    }

    @Test
    void shouldAllowSubmittingDraftLC() {
        final LCApplicationId id = LCApplicationId.randomId();
        fixture.given(new LCApplicationStartedEvent(id, ApplicantId.randomId(),
                                "My LC", LCState.DRAFT),
                        new LCAmountChangedEvent(id, THOUSAND_DOLLARS),
                        new MerchandiseChangedEvent(id, merchandise()))
                .when(new SubmitLCApplicationCommand(id))
                .expectEvents(new LCApplicationSubmittedEvent(id, THOUSAND_DOLLARS))
                .expectScheduledDeadlineWithName(Duration.ofDays(30), LCApplication.LC_APPROVAL_DEADLINE);
    }

    @Test
    void shouldNotifyApprovers30DaysAfterSubmission() {
        final LCApplicationId id = LCApplicationId.randomId();
        fixture.given(new LCApplicationStartedEvent(id, ApplicantId.randomId(),
                                "My LC", LCState.DRAFT),
                        new LCAmountChangedEvent(id, THOUSAND_DOLLARS),
                        new MerchandiseChangedEvent(id, merchandise()))
                .andGivenCommands(new SubmitLCApplicationCommand(id))
                .whenThenTimeElapses(Duration.ofDays(30))
                .expectEvents(new LCApprovalDeadlineReachedEvent(id));
    }

    @Test
    void shouldNotNotifyApprovers30DaysAfterSubmissionIfNotInSubmittedState() {
        final LCApplicationId id = LCApplicationId.randomId();
        fixture.given(new LCApplicationStartedEvent(id, ApplicantId.randomId(),
                                "My LC", LCState.DRAFT),
                        new LCAmountChangedEvent(id, THOUSAND_DOLLARS),
                        new MerchandiseChangedEvent(id, merchandise()))
                .andGivenCommands(
                        new SubmitLCApplicationCommand(id),
                        new ApproveLCApplicationCommand(id))
                .whenThenTimeElapses(Duration.ofDays(30))
                .expectNoScheduledDeadlines();
    }

    @Test
    void shouldNotAllowSubmittingWithoutValidAmount() {
        final LCApplicationId id = LCApplicationId.randomId();
        fixture.given(new LCApplicationStartedEvent(id, ApplicantId.randomId(),
                        "My LC", LCState.DRAFT))
                .when(new SubmitLCApplicationCommand(id))
                .expectException(LCAmountMissingException.class);
    }

    @Test
    void shouldNotAllowSubmitMoreThanOnce() {
        final LCApplicationId id = LCApplicationId.randomId();
        fixture.given(new LCApplicationStartedEvent(id, ApplicantId.randomId(),
                                "My LC", LCState.DRAFT),
                        new LCAmountChangedEvent(id, THOUSAND_DOLLARS),
                        new MerchandiseChangedEvent(id, merchandise()),
                        new LCApplicationSubmittedEvent(id, THOUSAND_DOLLARS))
                .when(new SubmitLCApplicationCommand(id))
                .expectException(LCApplicationAlreadySubmittedException.class);
    }

    @Test
    void shouldAllowChangingMerchandiseBeforeSubmit() {
        final LCApplicationId id = LCApplicationId.randomId();
        final Merchandise merchandise = merchandise();
        final ChangeMerchandiseCommand changeMerchandise = changeMerchandise(id, merchandise);
        fixture.given(new LCApplicationStartedEvent(id, ApplicantId.randomId(),
                        "My LC", LCState.DRAFT))
                .when(changeMerchandise)
                .expectEvents(new MerchandiseChangedEvent(id, merchandise));

    }

    @Test
    void shouldNotAllowChangingMerchandiseAfterSubmit() {
        final LCApplicationId id = LCApplicationId.randomId();
        fixture.given(new LCApplicationStartedEvent(id, ApplicantId.randomId(),
                                "My LC", LCState.DRAFT),
                        new LCAmountChangedEvent(id, THOUSAND_DOLLARS),
                        new MerchandiseChangedEvent(id, merchandise()),
                        new LCApplicationSubmittedEvent(id, THOUSAND_DOLLARS))
                .when(changeMerchandise(id, merchandise()))
                .expectException(LCApplicationAlreadySubmittedException.class);
    }

    @Test
    void shouldNotAllowMerchandiseWithoutItems() {
        final LCApplicationId id = LCApplicationId.randomId();
        assertThrows(JSR303ViolationException.class, () ->
                fixture.given(new LCApplicationStartedEvent(id, ApplicantId.randomId(),
                                        "My LC", LCState.DRAFT),
                                new LCAmountChangedEvent(id, THOUSAND_DOLLARS))
                        .when(changeMerchandise(id, merchandise(Set.of())))
        );
    }

    @Test
    void shouldAllowApprovingOfSubmittedLC() {
        final LCApplicationId id = LCApplicationId.randomId();
        fixture.given(new LCApplicationStartedEvent(id, ApplicantId.randomId(),
                                "My LC", LCState.DRAFT),
                        new LCAmountChangedEvent(id, THOUSAND_DOLLARS),
                        new MerchandiseChangedEvent(id, merchandise()))
                .andGivenCommands(new SubmitLCApplicationCommand(id))
                .when(new ApproveLCApplicationCommand(id))
                .expectEvents(new LCApplicationApprovedEvent(id))
                .expectNoScheduledDeadlines();
    }

    @Test
    void shouldAllowDecliningOfSubmittedLC() {
        final LCApplicationId id = LCApplicationId.randomId();
        fixture.given(new LCApplicationStartedEvent(id, ApplicantId.randomId(),
                                "My LC", LCState.DRAFT),
                        new LCAmountChangedEvent(id, THOUSAND_DOLLARS),
                        new MerchandiseChangedEvent(id, merchandise()))
                .andGivenCommands(new SubmitLCApplicationCommand(id))
                .when(new DeclineLCApplicationCommand(id))
                .expectEvents(new LCApplicationDeclinedEvent(id))
                .expectNoScheduledDeadlines();
    }

    @Test
    void shouldNotAllowApprovingOfLCNotInSubmittedState() {
        final LCApplicationId id = LCApplicationId.randomId();
        fixture.given(new LCApplicationStartedEvent(id, ApplicantId.randomId(),
                                "My LC", LCState.DRAFT),
                        new LCAmountChangedEvent(id, THOUSAND_DOLLARS),
                        new MerchandiseChangedEvent(id, merchandise()),
                        new LCApplicationSubmittedEvent(id, THOUSAND_DOLLARS),
                        new LCApplicationDeclinedEvent(id))
                .when(new ApproveLCApplicationCommand(id))
                .expectNoEvents();
    }

    @Test
    void shouldNotAllowDecliningOfLCNotInSubmittedState() {
        final LCApplicationId id = LCApplicationId.randomId();
        fixture.given(new LCApplicationStartedEvent(id, ApplicantId.randomId(),
                                "My LC", LCState.DRAFT),
                        new LCAmountChangedEvent(id, THOUSAND_DOLLARS),
                        new MerchandiseChangedEvent(id, merchandise()),
                        new LCApplicationSubmittedEvent(id, THOUSAND_DOLLARS),
                        new LCApplicationApprovedEvent(id))
                .when(new DeclineLCApplicationCommand(id))
                .expectNoEvents();
    }
}
