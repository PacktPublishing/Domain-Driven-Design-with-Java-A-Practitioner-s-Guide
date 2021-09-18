package com.premonition.lc.ch08.domain.sagas;

import com.premonition.lc.ch08.domain.ApplicantId;
import com.premonition.lc.ch08.domain.LCApplicationId;
import com.premonition.lc.ch08.domain.ProductId;
import com.premonition.lc.ch08.domain.commands.ApproveLCApplicationCommand;
import com.premonition.lc.ch08.domain.events.outbound.LCApplicationApprovedEvent;
import com.premonition.lc.ch08.domain.events.outbound.LCApplicationSubmittedEvent;
import com.premonition.lc.ch08.domain.events.inbound.ApplicantValidatedEvent;
import com.premonition.lc.ch08.domain.events.inbound.ProductLegalityValidatedEvent;
import com.premonition.lc.ch08.domain.events.inbound.ProductValueValidatedEvent;
import org.axonframework.test.saga.SagaTestFixture;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import java.util.List;
import java.util.stream.Stream;

class AutoApprovalSagaTests {

    private static final Money THOUSAND_DOLLARS = Money.of(1000,
            Monetary.getCurrency("USD"));
    private static final MonetaryAmount FIFTY_THOUSAND_DOLLARS = Money.of(50_000,
            Monetary.getCurrency("USD"));
    private SagaTestFixture<AutoApprovalSaga> fixture;

    private static Stream<Arguments> rejectedDecision() {
        final LCApplicationId lcApplicationId = LCApplicationId.randomId();
        final ApplicantId applicantId = ApplicantId.randomId();
        final ProductId productId = ProductId.randomId();

        final ApplicantValidatedEvent applicant = ApplicantValidatedEvent.rejected(lcApplicationId, applicantId, "Rejected!");
        final ProductLegalityValidatedEvent legality = ProductLegalityValidatedEvent.rejected(lcApplicationId, productId, "Rejected!");
        final ProductValueValidatedEvent value = ProductValueValidatedEvent.rejected(lcApplicationId, productId, "Rejected!");
        return Stream.of(
                Arguments.of(lcApplicationId, applicant),
                Arguments.of(lcApplicationId, legality),
                Arguments.of(lcApplicationId, value)
        );
    }

    private static Stream<Arguments> autoApprove() {
        final LCApplicationId lcApplicationId = LCApplicationId.randomId();
        final ApplicantId applicantId = ApplicantId.randomId();
        final ProductId productId = ProductId.randomId();

        final LCApplicationSubmittedEvent submitted = new LCApplicationSubmittedEvent(lcApplicationId, THOUSAND_DOLLARS);
        final ApplicantValidatedEvent applicant = ApplicantValidatedEvent.approved(lcApplicationId, applicantId);
        final ProductLegalityValidatedEvent legality = ProductLegalityValidatedEvent.approved(lcApplicationId, productId);
        final ProductValueValidatedEvent value = ProductValueValidatedEvent.approved(lcApplicationId, productId);

        return Stream.of(
                Arguments.of(lcApplicationId, List.of(submitted, applicant, legality), value),
                Arguments.of(lcApplicationId, List.of(submitted, applicant, value), legality),
                Arguments.of(lcApplicationId, List.of(submitted, legality, value), applicant)
        );
    }

    private static Stream<Arguments> doNotAutoApproveAndEnd() {
        final LCApplicationId lcApplicationId = LCApplicationId.randomId();
        final ApplicantId applicantId = ApplicantId.randomId();
        final ProductId productId = ProductId.randomId();

        final LCApplicationSubmittedEvent submitted = new LCApplicationSubmittedEvent(lcApplicationId, FIFTY_THOUSAND_DOLLARS);
        final ApplicantValidatedEvent applicant = ApplicantValidatedEvent.approved(lcApplicationId, applicantId);
        final ProductLegalityValidatedEvent legality = ProductLegalityValidatedEvent.approved(lcApplicationId, productId);
        final ProductValueValidatedEvent value = ProductValueValidatedEvent.approved(lcApplicationId, productId);

        return Stream.of(
                Arguments.of(lcApplicationId, List.of(submitted, applicant, legality), value),
                Arguments.of(lcApplicationId, List.of(submitted, applicant, value), legality),
                Arguments.of(lcApplicationId, List.of(submitted, legality, value), applicant)
        );
    }

    @BeforeEach
    void setUp() {
        fixture = new SagaTestFixture<>(AutoApprovalSaga.class);
    }

    @Test
    void shouldStartSagaOnSubmit() {
        final LCApplicationId lcApplicationId = LCApplicationId.randomId();
        fixture.givenAggregate(lcApplicationId.toString()).published()
                .whenPublishingA(
                        new LCApplicationSubmittedEvent(lcApplicationId, THOUSAND_DOLLARS))
                .expectActiveSagas(1);
    }

    @ParameterizedTest(name = "auto approve {index}")
    @MethodSource("autoApprove")
    @DisplayName("auto approve")
    void shouldAutoApprove(LCApplicationId lcApplicationId, List<?> givenEvents, Object when) {
        fixture.givenAggregate(lcApplicationId.toString()).published(givenEvents.toArray())
                .whenPublishingA(when)
                .expectActiveSagas(1)
                .expectDispatchedCommands(new ApproveLCApplicationCommand(lcApplicationId));
    }

    @ParameterizedTest(name = "do not auto approve due to threshold {index}")
    @MethodSource("doNotAutoApproveAndEnd")
    void shouldNotAutoApprove(LCApplicationId lcApplicationId, List<?> givenEvents, Object when) {
        fixture.givenAggregate(lcApplicationId.toString()).published(givenEvents.toArray())
                .whenPublishingA(when)
                .expectActiveSagas(0)
                .expectNoDispatchedCommands();
    }

    @ParameterizedTest
    @MethodSource("rejectedDecision")
    void shouldEndSagaOnRejectedDecision(LCApplicationId lcApplicationId, Object rejectionEvent) {

        fixture.givenAggregate(lcApplicationId.toString()).published(
                        new LCApplicationSubmittedEvent(lcApplicationId, THOUSAND_DOLLARS)
                ).whenPublishingA(rejectionEvent)
                .expectActiveSagas(0)
                .expectNoDispatchedCommands();
    }

    @Test
    void shouldEndSagaAfterAutoApproval() {
        final LCApplicationId lcApplicationId = LCApplicationId.randomId();
        final ApplicantId applicantId = ApplicantId.randomId();
        final ProductId productId = ProductId.randomId();

        final LCApplicationSubmittedEvent submitted = new LCApplicationSubmittedEvent(lcApplicationId, THOUSAND_DOLLARS);
        final ApplicantValidatedEvent applicant = ApplicantValidatedEvent.approved(lcApplicationId, applicantId);
        final ProductLegalityValidatedEvent legality = ProductLegalityValidatedEvent.approved(lcApplicationId, productId);
        final ProductValueValidatedEvent value = ProductValueValidatedEvent.approved(lcApplicationId, productId);

        fixture.givenAggregate(lcApplicationId.toString()).published(
                        submitted, applicant, legality, value)
                .whenPublishingA(new LCApplicationApprovedEvent(lcApplicationId))
                .expectActiveSagas(0)
                .expectNoDispatchedCommands();
    }
}