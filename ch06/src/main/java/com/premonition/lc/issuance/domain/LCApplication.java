package com.premonition.lc.issuance.domain;

import com.premonition.lc.issuance.domain.commands.CreateLCApplicationCommand;
import com.premonition.lc.issuance.domain.commands.SaveLCApplicationCommand;
import com.premonition.lc.issuance.domain.events.LCApplicationCreatedEvent;
import com.premonition.lc.issuance.domain.events.LCApplicationSavedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class LCApplication {

    @AggregateIdentifier
    private LCApplicationId id;
    private State state;

    @SuppressWarnings("unused")
    private LCApplication() {
        // Required by the framework
    }

    @CommandHandler
    public LCApplication(CreateLCApplicationCommand command) {
        // TODO: perform validations here
        AggregateLifecycle.apply(new LCApplicationCreatedEvent(command.getId(),
                command.getApplicantId(), command.getClientReference()));
    }

    @CommandHandler
    public void save(SaveLCApplicationCommand command) {
        AggregateLifecycle.apply(new LCApplicationSavedEvent());
    }

    @EventSourcingHandler
    private void on(LCApplicationCreatedEvent event) {
        this.id = event.getId();
        this.state = State.DRAFT;
    }

    enum State {
        DRAFT, SUBMITTED, ISSUED
    }
}
