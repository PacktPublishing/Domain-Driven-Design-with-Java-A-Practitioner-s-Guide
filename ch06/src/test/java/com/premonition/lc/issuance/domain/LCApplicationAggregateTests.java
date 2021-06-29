package com.premonition.lc.issuance.domain;

import com.premonition.lc.issuance.domain.commands.CreateLCApplicationCommand;
import com.premonition.lc.issuance.domain.commands.SaveLCApplicationCommand;
import com.premonition.lc.issuance.domain.events.LCApplicationCreatedEvent;
import com.premonition.lc.issuance.domain.events.LCApplicationSavedEvent;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.axonframework.test.matchers.Matchers.andNoMore;
import static org.axonframework.test.matchers.Matchers.exactSequenceOf;
import static org.axonframework.test.matchers.Matchers.messageWithPayload;
import static org.hamcrest.Matchers.any;

public class LCApplicationAggregateTests {

    private FixtureConfiguration<LCApplication> fixture;

    @BeforeEach
    void setUp() {
        fixture = new AggregateTestFixture<>(LCApplication.class);
    }

    @Test
    void shouldPublishLCApplicationCreated() {
        fixture.given()

                .when(new CreateLCApplicationCommand("admin", "My Test"))

                .expectEventsMatching(exactSequenceOf(
                        messageWithPayload(any(LCApplicationCreatedEvent.class)),
                        andNoMore()
                ));
    }

    @Test
    void shouldSaveLCApplication() {
        final LCApplicationId id = LCApplicationId.randomId();
        final LCApplicationCreatedEvent created = new LCApplicationCreatedEvent(id,
                "test-user", "Test LC");
        fixture.given(created)
                .when(new SaveLCApplicationCommand(id))
                .expectEvents(new LCApplicationSavedEvent());
    }
}
