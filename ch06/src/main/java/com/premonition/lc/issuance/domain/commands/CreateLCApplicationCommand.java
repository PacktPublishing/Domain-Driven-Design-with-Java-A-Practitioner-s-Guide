package com.premonition.lc.issuance.domain.commands;

import com.premonition.lc.issuance.domain.LCApplicationId;
import lombok.Data;

@Data
public class CreateLCApplicationCommand {
    private final String applicantId;
    private final LCApplicationId id;
    private final String clientReference;

    public CreateLCApplicationCommand(String applicantId, String clientReference) {
        this.id = LCApplicationId.randomId();
        this.applicantId = applicantId;
        this.clientReference = clientReference;
    }
}
