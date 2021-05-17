package com.premonition.lc.issuance.domain.ch05;

import com.premonition.lc.issuance.domain.Country;
import com.premonition.lc.issuance.domain.DomainException;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;

import java.util.Set;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

public class LCApplication {

    @AggregateIdentifier                                                            // <1>
    private LCApplicationId id;
    private State state;

    @SuppressWarnings("unused")
    private LCApplication() {
        // Required by the framework
    }

    @CommandHandler                                                                 // <2>
    public LCApplication(CreateLCApplicationCommand command) {                      // <3>
        // TODO: perform validations here
        AggregateLifecycle.apply(new LCApplicationCreatedEvent(command.getId()));   // <4>
    }

    @EventSourcingHandler                                                           // <5>
    private void on(LCApplicationCreatedEvent event) {
        this.id = event.getId();
        this.state = State.DRAFT;
    }

    public LCApplication(CreateLCApplicationCommand command, Set<Country> sanctioned) {
        if (sanctioned.contains(command.getBeneficiaryCountry())) {
            throw new CannotTradeWithSanctionedCountryException();
        }
        apply(new LCApplicationCreatedEvent(command.getId()));
    }

    enum State {
        DRAFT, SUBMITTED, ISSUED
    }

    @CommandHandler
    public void submit(SubmitLCApplicationCommand command) {
        if (this.state != State.DRAFT) {                                     // <1>
            throw new AlreadySubmittedException("LC is already submitted!"); // <1>
        }
        apply(new LCApplicationSubmittedEvent(id));
    }

    @EventSourcingHandler
    private void on(LCApplicationSubmittedEvent event) {
        this.state = State.SUBMITTED;
    }
}
