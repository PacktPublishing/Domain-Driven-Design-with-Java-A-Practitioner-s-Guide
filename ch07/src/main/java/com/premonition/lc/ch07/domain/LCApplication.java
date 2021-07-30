package com.premonition.lc.ch07.domain;

import com.premonition.lc.ch07.domain.commands.StartNewLCApplicationCommand;
import com.premonition.lc.ch07.domain.commands.SaveLCApplicationCommand;
import com.premonition.lc.ch07.domain.events.LCApplicationStartedEvent;
import com.premonition.lc.ch07.domain.events.LCApplicationSavedEvent;
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
    public LCApplication(StartNewLCApplicationCommand command) {
        // TODO: perform validations here
        AggregateLifecycle.apply(new LCApplicationStartedEvent(command.getId(),
                command.getApplicantId(), command.getClientReference()));
    }

    @CommandHandler
    public void save(SaveLCApplicationCommand command) {
        AggregateLifecycle.apply(new LCApplicationSavedEvent());
    }

    @EventSourcingHandler
    private void on(LCApplicationStartedEvent event) {
        this.id = event.getId();
        this.state = State.DRAFT;
    }

    enum State {
        DRAFT, SUBMITTED, ISSUED
    }
}
