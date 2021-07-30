package com.premonition.lc.ch07.domain;

import com.premonition.lc.ch07.domain.commands.SaveLCApplicationCommand;
import com.premonition.lc.ch07.domain.events.LCApplicationStartedEvent;
import com.premonition.lc.ch07.domain.events.LCApplicationSavedEvent;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.premonition.lc.ch07.domain.commands.StartNewLCApplicationCommand.startApplication;
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
    void shouldPublishLCApplicationIsStarted() {
        fixture.given()

                .when(startApplication("admin", "My Test"))

                .expectEventsMatching(exactSequenceOf(
                        messageWithPayload(any(LCApplicationStartedEvent.class)),
                        andNoMore()
                ));
    }

    @Test
    void shouldSaveLCApplication() {
        final LCApplicationId id = LCApplicationId.randomId();
        final LCApplicationStartedEvent started = new LCApplicationStartedEvent(id,
                "test-user", "Test LC");
        fixture.given(started)
                .when(new SaveLCApplicationCommand(id))
                .expectEvents(new LCApplicationSavedEvent());
    }
}
