package com.premonition.lc.issuance.domain.commands;

import com.premonition.lc.issuance.domain.DocumentClause;
import com.premonition.lc.issuance.domain.LCId;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
public class AddDocumentClauseCommand {
    @TargetAggregateIdentifier
    private final LCId id;
    private final DocumentClause documentClause;

}
