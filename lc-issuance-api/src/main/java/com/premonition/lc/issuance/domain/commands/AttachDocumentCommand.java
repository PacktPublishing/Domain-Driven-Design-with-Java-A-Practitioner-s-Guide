package com.premonition.lc.issuance.domain.commands;

import com.premonition.lc.issuance.domain.Document;
import com.premonition.lc.issuance.domain.LCId;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
public class AttachDocumentCommand {
    @TargetAggregateIdentifier
    private final LCId id;
    private final Document document;

}
