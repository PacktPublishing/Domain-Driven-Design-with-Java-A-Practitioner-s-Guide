package com.premonition.lc.ch05.domain.commands;

import com.premonition.lc.ch05.domain.AdvisingBank;
import com.premonition.lc.ch05.domain.LCApplicationId;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
public class ChangeAdvisingBankCommand {

    @TargetAggregateIdentifier
    private final LCApplicationId applicationId;
    private final AdvisingBank advisingBank;
}
