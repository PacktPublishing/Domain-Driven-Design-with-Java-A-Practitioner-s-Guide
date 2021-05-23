package com.premonition.lc.issuance.domain.ch05;

import com.premonition.lc.issuance.domain.AdvisingBank;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
public class ChangeAdvisingBankCommand {

    @TargetAggregateIdentifier
    private final LCApplicationId applicationId;
    private final AdvisingBank advisingBank;
}
