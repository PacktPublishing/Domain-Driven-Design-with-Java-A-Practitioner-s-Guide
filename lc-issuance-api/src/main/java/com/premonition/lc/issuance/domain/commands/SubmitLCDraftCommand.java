package com.premonition.lc.issuance.domain.commands;

import com.premonition.lc.issuance.domain.LCId;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
public class SubmitLCDraftCommand {
    @TargetAggregateIdentifier
    private final LCId id;

    public SubmitLCDraftCommand(LCId id) {
        this.id = id;
    }
}
