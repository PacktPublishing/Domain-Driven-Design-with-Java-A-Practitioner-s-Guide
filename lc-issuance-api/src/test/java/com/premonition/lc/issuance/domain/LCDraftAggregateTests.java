package com.premonition.lc.issuance.domain;

import com.premonition.lc.issuance.domain.commands.InitiateLCDraftCommand;
import com.premonition.lc.issuance.domain.events.LCDraftInitiatedEvent;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class LCDraftAggregateTests {

    private AggregateTestFixture<LCDraft> fixture;

    @BeforeEach
    void setUp() {
        fixture = new AggregateTestFixture<>(LCDraft.class);
    }

    @Test
    void shouldPublishDraftInitiatedEvent() {
        final UUID id = UUID.randomUUID();
        fixture.given()
                .when(new InitiateLCDraftCommand(id))
                .expectEvents(new LCDraftInitiatedEvent(id));
    }
}
