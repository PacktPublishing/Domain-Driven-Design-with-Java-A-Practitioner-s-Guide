package com.premonition.lc.issuance.domain;

import com.premonition.lc.issuance.domain.commands.*;
import com.premonition.lc.issuance.domain.events.DocumentClauseAddedEvent;
import com.premonition.lc.issuance.domain.events.DocumentClauseRemovedEvent;
import com.premonition.lc.issuance.domain.events.LCDraftCreatedEvent;
import com.premonition.lc.issuance.domain.events.LCDraftSubmittedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;

import java.util.HashSet;
import java.util.Set;

import static com.premonition.lc.issuance.domain.LCDraft.State.SUBMITTED;
import static org.axonframework.modelling.command.AggregateLifecycle.apply;

public class LCDraft {

    @AggregateIdentifier
    private LCId id;
    private State state;
    private Set<DocumentClause> documentClauses;

    @SuppressWarnings("unused")
    private LCDraft() {
        // Required by axon framework
    }

    @CommandHandler
    private LCDraft(CreateLCDraftCommand command) {
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
    private void issue(IssueLCCommand command) {
        if (state == State.DRAFT) {
            throw new DomainException("Cannot issue LC in draft state!");
        }
    }

    @CommandHandler
    private void addDocumentClause(AddDocumentClauseCommand command) {
        if (!documentClauses.contains(command.getDocumentClause())) {
            apply(new DocumentClauseAddedEvent(id, command.getDocumentClause()));
        }
    }

    @CommandHandler
    private void removeDocumentClause(RemoveDocumentClauseCommand command) {
        if (!documentClauses.contains(command.getDocumentClause())) {
            throw new DocumentClauseNotAddedException();
        }
        apply(new DocumentClauseRemovedEvent(id, command.getDocumentClause()));
    }

    @CommandHandler
    private void submit(SubmitLCDraftCommand command) {
        if (!documentClauses.contains(DocumentClause.CERTIFICATE_OF_ORIGIN)) {
            throw new MissingRequiredDocumentException("Cannot submit without attaching a certificate of origin document.");
        }
        if (state != State.DRAFT) {
            throw new DomainException("");
        }
        apply(new LCDraftSubmittedEvent(command.getId()));
    }

    @EventSourcingHandler
    private void on(LCDraftCreatedEvent event) {
        this.id = event.getId();
        this.state = State.DRAFT;
        this.documentClauses = new HashSet<>();
    }

    @EventSourcingHandler
    private void on(DocumentClauseAddedEvent event) {
        this.documentClauses.add(event.getDocumentClause());
    }

    @EventSourcingHandler
    private void on(DocumentClauseRemovedEvent event) {
        this.documentClauses.remove(event.getDocumentClause());
    }

    @EventSourcingHandler
    private void on(LCDraftSubmittedEvent event) {
        this.state = SUBMITTED;
    }


    public enum State {
        DRAFT, SUBMITTED, ISSUED
    }
}
