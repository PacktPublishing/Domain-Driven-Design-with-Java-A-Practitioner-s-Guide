package com.premonition.lc.issuance.domain.commands;

import com.premonition.lc.issuance.domain.LCApplicationId;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
public class SaveLCApplicationCommand {
    @TargetAggregateIdentifier
    private final LCApplicationId id;

}
