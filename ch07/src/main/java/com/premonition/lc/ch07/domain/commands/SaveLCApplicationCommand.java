package com.premonition.lc.ch07.domain.commands;

import com.premonition.lc.ch07.domain.LCApplicationId;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
public class SaveLCApplicationCommand {
    @TargetAggregateIdentifier
    private final LCApplicationId id;

}
