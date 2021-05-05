package com.premonition.lc.issuance.domain.ch05;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;

import java.util.UUID;

public class LCApplication {

    @AggregateIdentifier
    private UUID id;

    @SuppressWarnings("unused")
    private LCApplication() {
        // Required by the framework
    }

    @CommandHandler
    public LCApplication(CreateLCApplicationCommand command) {
        AggregateLifecycle.apply(new LCApplicationCreatedEvent(UUID.randomUUID()));
    }

    @EventSourcingHandler
    private void on(LCApplicationCreatedEvent event) {
        this.id = event.getId();
    }
}
