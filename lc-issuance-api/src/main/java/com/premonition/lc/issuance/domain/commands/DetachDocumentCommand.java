package com.premonition.lc.issuance.domain.commands;

import com.premonition.lc.issuance.domain.Document;
import com.premonition.lc.issuance.domain.LCId;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
public class DetachDocumentCommand {
    @TargetAggregateIdentifier
    private final LCId id;
    @NotNull
    @Valid
    private final Document document;

    public DetachDocumentCommand(LCId id, Document document) {
        this.id = id;
        this.document = document;
    }
}
