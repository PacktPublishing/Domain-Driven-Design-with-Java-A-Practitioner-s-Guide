package com.premonition.lc.ch08.domain.sagas.events;

import com.premonition.lc.ch08.domain.ApplicantId;
import com.premonition.lc.ch08.domain.LCApplicationId;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApplicantValidatedEvent implements Decisionable {
    private LCApplicationId lcApplicationId;
    private ApplicantId applicantId;
    private Decision decision;

    public static ApplicantValidatedEvent approved(LCApplicationId lcApplicationId, ApplicantId applicantId) {
        return new ApplicantValidatedEvent(lcApplicationId, applicantId, Decision.accepted());
    }

    public static ApplicantValidatedEvent rejected(LCApplicationId lcApplicationId, ApplicantId applicantId, String remarks) {
        return new ApplicantValidatedEvent(lcApplicationId, applicantId, Decision.rejected(remarks));
    }
}
