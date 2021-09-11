package com.premonition.lc.ch08.domain.commands;

import com.premonition.lc.ch08.domain.LCApplicationId;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.money.MonetaryAmount;
import javax.validation.constraints.Positive;

@Data
public class ChangeLCAmountCommand {

    @TargetAggregateIdentifier
    private final LCApplicationId id;
    @Positive
    private final MonetaryAmount amount;
}
