package com.premonition.lc.issuance.domain.ch05;

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