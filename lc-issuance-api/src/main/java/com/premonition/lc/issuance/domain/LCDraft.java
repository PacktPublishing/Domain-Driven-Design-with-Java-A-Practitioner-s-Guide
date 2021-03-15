package com.premonition.lc.issuance.domain;

import com.premonition.lc.issuance.domain.commands.InitiateLCDraftCommand;
import com.premonition.lc.issuance.domain.events.LCDraftInitiatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;

import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

public class LCDraft {
    @AggregateIdentifier
    private UUID id;

    @SuppressWarnings("unused")
    private LCDraft() {
        // Required by axon framework
    }

    @CommandHandler
    LCDraft(InitiateLCDraftCommand command) {
        apply(new LCDraftInitiatedEvent(command.getId()));
    }

    @EventSourcingHandler
    private void on(LCDraftInitiatedEvent event) {
        this.id = event.getId();
    }
}
