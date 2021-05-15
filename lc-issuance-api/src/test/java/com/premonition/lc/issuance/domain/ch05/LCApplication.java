package com.premonition.lc.issuance.domain.ch05;

import com.premonition.lc.issuance.domain.Country;
import com.premonition.lc.issuance.domain.DomainException;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;

import java.util.Set;

public class LCApplication {

    @AggregateIdentifier                                                            // <1>
    private LCApplicationId id;

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
    }

    public LCApplication(CreateLCApplicationCommand command, Set<Country> sanctioned) {
        if (sanctioned.contains(command.getBeneficiaryCountry())) {
            throw new DomainException("Import from this country is currently prohibited!");
        }
        AggregateLifecycle.apply(new LCApplicationCreatedEvent(command.getId()));
    }
}
