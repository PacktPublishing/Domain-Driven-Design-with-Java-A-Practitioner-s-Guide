package com.premonition.lc.issuance.domain.commands;

import com.premonition.lc.issuance.domain.LCApplicationId;
import lombok.Data;

@Data
public class StartNewLCApplicationCommand {
    private final String applicantId;
    private final LCApplicationId id;
    private final String clientReference;

    public StartNewLCApplicationCommand(String applicantId, String clientReference) {
        this.id = LCApplicationId.randomId();
        this.applicantId = applicantId;
        this.clientReference = clientReference;
    }
}
