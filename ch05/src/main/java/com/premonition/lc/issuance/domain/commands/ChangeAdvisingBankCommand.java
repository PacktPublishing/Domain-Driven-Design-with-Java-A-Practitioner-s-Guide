package com.premonition.lc.issuance.domain.commands;

import com.premonition.lc.issuance.domain.AdvisingBank;
import com.premonition.lc.issuance.domain.LCApplicationId;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
public class ChangeAdvisingBankCommand {

    @TargetAggregateIdentifier
    private final LCApplicationId applicationId;
    private final AdvisingBank advisingBank;
}
