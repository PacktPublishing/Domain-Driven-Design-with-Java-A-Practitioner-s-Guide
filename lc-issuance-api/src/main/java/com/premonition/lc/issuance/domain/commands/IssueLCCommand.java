package com.premonition.lc.issuance.domain.commands;

import com.premonition.lc.issuance.domain.LCId;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
public class IssueLCCommand {
    @TargetAggregateIdentifier
    private final LCId lcId;

    public IssueLCCommand(LCId lcId) {
        this.lcId = lcId;
    }
}
