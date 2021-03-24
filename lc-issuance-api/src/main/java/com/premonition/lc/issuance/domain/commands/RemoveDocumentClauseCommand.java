package com.premonition.lc.issuance.domain.commands;

import com.premonition.lc.issuance.domain.DocumentClause;
import com.premonition.lc.issuance.domain.LCId;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
public class RemoveDocumentClauseCommand {
    @TargetAggregateIdentifier
    private final LCId id;
    @NotNull
    @Valid
    private final DocumentClause documentClause;

    public RemoveDocumentClauseCommand(LCId id, DocumentClause documentClause) {
        this.id = id;
        this.documentClause = documentClause;
    }
}
