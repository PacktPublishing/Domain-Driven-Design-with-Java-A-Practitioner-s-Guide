package com.premonition.lc.issuance.domain.ch05;

import com.premonition.lc.issuance.domain.AdvisingBank;
import com.premonition.lc.issuance.domain.BankId;
import com.premonition.lc.issuance.domain.Country;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.axonframework.test.matchers.Matchers.andNoMore;
import static org.axonframework.test.matchers.Matchers.exactSequenceOf;
import static org.axonframework.test.matchers.Matchers.messageWithPayload;
import static org.hamcrest.Matchers.any;

public class LCApplicationAggregateTests {

    private FixtureConfiguration<LCApplication> fixture;                          // <1>

    @BeforeEach
    void setUp() {
        fixture = new AggregateTestFixture<>(LCApplication.class);                // <2>
    }

    @Test
    void shouldPublishLCApplicationCreated() {
        fixture.given()                                                           // <3>

                .when(new CreateLCApplicationCommand())                           // <4>

                .expectEventsMatching(exactSequenceOf(                            // <5>
                        messageWithPayload(any(LCApplicationCreatedEvent.class)), // <6>
                        andNoMore()                                               // <7>
                ));
    }

    @Test
    void shouldNotAllowSubmitOnAnAlreadySubmittedLC() {
        final LCApplicationId applicationId = LCApplicationId.randomId();

        fixture.given(
                new LCApplicationCreatedEvent(applicationId),           // <1>
                new LCApplicationSubmittedEvent(applicationId))         // <1>

                .when(new SubmitLCApplicationCommand(applicationId))    // <2>

                .expectException(AlreadySubmittedException.class)       // <3>
                .expectNoEvents();                                      // <4>
    }

    @Test
    void shouldAllowSubmitOnlyInDraftState() {
        final LCApplicationId applicationId = LCApplicationId.randomId();

        fixture.given(new LCApplicationCreatedEvent(applicationId))            // <1>
                .when(new SubmitLCApplicationCommand(applicationId))           // <2>
                .expectEvents(new LCApplicationSubmittedEvent(applicationId)); // <3>
    }

    @Test
    void shouldAllowChangingAdvisingBank() {
        final LCApplicationId applicationId = LCApplicationId.randomId();

        final AdvisingBank oldAdvisingBank = AdvisingBank.builder()
                .id(BankId.randomId())
                .country(Country.SOKOVIA).build();
        final AdvisingBank newAdvisingBank = AdvisingBank.builder()
                .id(BankId.randomId())
                .country(Country.WAKANDA).build();

        fixture.given(new LCApplicationCreatedEvent(applicationId, oldAdvisingBank))
                .when(new ChangeAdvisingBankCommand(applicationId, newAdvisingBank))
                .expectEvents(new AdvisingBankChangedEvent(applicationId, newAdvisingBank));
    }
}
