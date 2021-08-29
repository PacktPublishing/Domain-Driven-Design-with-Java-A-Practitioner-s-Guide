package com.premonition.lc.ch07.domain.commands;

import com.premonition.lc.ch07.domain.ApplicantId;
import com.premonition.lc.ch07.domain.LCApplicationId;
import lombok.Data;

@Data
public class StartNewLCApplicationCommand {
    private final ApplicantId applicantId;
    private final LCApplicationId id;
    private final String clientReference;

    private StartNewLCApplicationCommand(ApplicantId applicantId,
                                         String clientReference) {
        this.id = LCApplicationId.randomId();
        this.applicantId = applicantId;
        this.clientReference = clientReference;
    }

    public static StartNewLCApplicationCommand startApplication(ApplicantId applicantId, String clientReference) {
        return new StartNewLCApplicationCommand(applicantId, clientReference);
    }
}
