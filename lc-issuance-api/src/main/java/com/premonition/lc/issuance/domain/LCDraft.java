package com.premonition.lc.issuance.domain;

import com.premonition.lc.issuance.domain.commands.CreateLCDraftCommand;
import com.premonition.lc.issuance.domain.commands.IssueLCCommand;
import com.premonition.lc.issuance.domain.events.LCDraftCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

public class LCDraft {

    @AggregateIdentifier
    private LCId id;
    private State state;

    @SuppressWarnings("unused")
    private LCDraft() {
        // Required by axon framework
    }

    @CommandHandler
    LCDraft(CreateLCDraftCommand command) {
        validateCountryOfBeneficiary(command.countryOfBeneficiary(), command.countryOfApplicant());
        validateAdvisingBankCountry(command.countryOfAdvisingBank(), command.countryOfBeneficiary());
        apply(new LCDraftCreatedEvent(command.getId()));
    }

    private void validateAdvisingBankCountry(Country ofAdvisingBank, Country ofBeneficiary) {
        if (!ofAdvisingBank.equals(ofBeneficiary)) {
            throw new InvalidAdvisingBankException(ofBeneficiary);
        }
    }

    private void validateCountryOfBeneficiary(Country ofBeneficiary, Country ofApplicant) {
        if (ofBeneficiary.equals(ofApplicant)) {
            throw new InvalidBeneficiaryCountryException(ofApplicant);
        }
    }

    @CommandHandler
    void issue(IssueLCCommand command) {
        if (state == State.DRAFT) {
            throw new DomainException("Cannot issue LC in draft state!");
        }
    }

    @EventSourcingHandler
    private void on(LCDraftCreatedEvent event) {
        this.id = event.getId();
        this.state = State.DRAFT;
    }

    public enum State {
        DRAFT
    }
}
