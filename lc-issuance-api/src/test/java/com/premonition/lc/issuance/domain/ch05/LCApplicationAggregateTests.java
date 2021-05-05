package com.premonition.lc.issuance.domain.ch05;

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
        CreateLCApplicationCommand command = new CreateLCApplicationCommand();    // <3>

        fixture.given()                                                           // <4>
                .when(command)                                                    // <5>
                .expectEventsMatching(exactSequenceOf(                            // <6>
                        messageWithPayload(any(LCApplicationCreatedEvent.class)), // <7>
                        andNoMore()                                               // <8>
                ));
    }
}
