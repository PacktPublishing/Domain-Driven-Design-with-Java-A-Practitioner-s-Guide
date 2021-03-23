package com.premonition.lc.issuance.domain;

import com.premonition.lc.issuance.domain.commands.AttachDocumentCommand;
import com.premonition.lc.issuance.domain.commands.CreateLCDraftCommand;
import com.premonition.lc.issuance.domain.commands.DetachDocumentCommand;
import com.premonition.lc.issuance.domain.commands.IssueLCCommand;
import com.premonition.lc.issuance.domain.events.DocumentAttachedEvent;
import com.premonition.lc.issuance.domain.events.DocumentDetachedEvent;
import com.premonition.lc.issuance.domain.events.LCDraftCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;

import java.util.HashSet;
import java.util.Set;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

public class LCDraft {

    @AggregateIdentifier
    private LCId id;
    private State state;
    private Set<Document> documents;

    @SuppressWarnings("unused")
    private LCDraft() {
        // Required by axon framework
    }

    @CommandHandler
    LCDraft(CreateLCDraftCommand command) {
        validateCountryOfBeneficiary(command.countryOfBeneficiary(), command.countryOfApplicant());
        validateCountryOfAdvisingBank(command.countryOfAdvisingBank(), command.countryOfBeneficiary());
        apply(new LCDraftCreatedEvent(command.getId()));
    }

    private void validateCountryOfAdvisingBank(Country ofAdvisingBank, Country ofBeneficiary) {
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

    @CommandHandler
    void attach(AttachDocumentCommand command) {
        apply(new DocumentAttachedEvent(id, command.getDocument()));
    }

    @CommandHandler
    void detach(DetachDocumentCommand command) {
        if (!documents.contains(command.getDocument())) {
            throw new DocumentNotAttachedException(command.getDocument());
        }
        apply(new DocumentDetachedEvent(id, command.getDocument()));
    }

    @EventSourcingHandler
    private void on(LCDraftCreatedEvent event) {
        this.id = event.getId();
        this.state = State.DRAFT;
        this.documents = new HashSet<>();
    }

    @EventSourcingHandler
    private void on(DocumentAttachedEvent event) {
        this.documents.add(event.getDocument());
    }

    @EventSourcingHandler
    private void on(DocumentDetachedEvent event) {
        this.documents.remove(event.getDocument());
    }

    public enum State {
        DRAFT, SUBMITTED, ISSUED
    }
}
