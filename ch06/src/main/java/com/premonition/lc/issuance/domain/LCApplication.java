package com.premonition.lc.issuance.domain;

import com.premonition.lc.issuance.domain.commands.ChangeAdvisingBankCommand;
import com.premonition.lc.issuance.domain.commands.CreateLCApplicationCommand;
import com.premonition.lc.issuance.domain.commands.SubmitLCApplicationCommand;
import com.premonition.lc.issuance.domain.events.AdvisingBankChangedEvent;
import com.premonition.lc.issuance.domain.events.LCApplicationCreatedEvent;
import com.premonition.lc.issuance.domain.events.LCApplicationSubmittedEvent;
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
    private AdvisingBank advisingBank;

    @SuppressWarnings("unused")
    private LCApplication() {
        // Required by the framework
    }

    @CommandHandler                                                                 // <2>
    public LCApplication(CreateLCApplicationCommand command) {                      // <3>
        // TODO: perform validations here
        AggregateLifecycle.apply(new LCApplicationCreatedEvent(command.getId()));   // <4>
    }

    public LCApplication(CreateLCApplicationCommand command, Set<Country> sanctioned) {
        if (sanctioned.contains(command.getBeneficiaryCountry())) {
            throw new CannotTradeWithSanctionedCountryException();
        }
        apply(new LCApplicationCreatedEvent(command.getId()));
    }

    @EventSourcingHandler                                                           // <5>
    private void on(LCApplicationCreatedEvent event) {
        this.id = event.getId();
        this.state = State.DRAFT;
        this.advisingBank = event.getAdvisingBank();
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

    @CommandHandler
    public void changeAdvisingBank(ChangeAdvisingBankCommand command) {
        if (!command.getAdvisingBank().equals(this.advisingBank)) {

            apply(new AdvisingBankChangedEvent(id, command.getAdvisingBank()));
        }
    }

    @EventSourcingHandler
    private void on(AdvisingBankChangedEvent event) {
        this.advisingBank = event.getAdvisingBank();
    }

    enum State {
        DRAFT, SUBMITTED, ISSUED
    }
}
