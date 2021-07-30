package com.premonition.lc.ch05.domain.commands;

import com.premonition.lc.ch05.domain.LCApplicationId;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
public class SubmitLCApplicationCommand {
    @TargetAggregateIdentifier // <1>
    private final LCApplicationId applicationId;

    public SubmitLCApplicationCommand(LCApplicationId applicationId) {
        this.applicationId = applicationId;
    }
}