package com.premonition.lc.ch09.domain.commands;

import com.premonition.lc.ch09.domain.ApplicantId;
import com.premonition.lc.ch09.domain.LCApplicationId;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class StartNewLCApplicationCommand {
    @NotNull
    private final ApplicantId applicantId;
    @NotNull
    private final LCApplicationId id;
    @Size(min = 4)
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
