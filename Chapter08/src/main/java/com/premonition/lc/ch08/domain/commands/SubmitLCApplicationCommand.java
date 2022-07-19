package com.premonition.lc.ch08.domain.commands;

import com.premonition.lc.ch08.domain.LCApplicationId;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
public class SubmitLCApplicationCommand {

    @TargetAggregateIdentifier
    private final LCApplicationId id;

}
