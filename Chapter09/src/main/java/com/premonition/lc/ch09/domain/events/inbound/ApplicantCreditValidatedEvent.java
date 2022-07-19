package com.premonition.lc.ch09.domain.events.inbound;

import com.premonition.lc.ch09.domain.ApplicantId;
import com.premonition.lc.ch09.domain.Decision;
import com.premonition.lc.ch09.domain.LCApplicationId;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApplicantCreditValidatedEvent {
    private LCApplicationId lcApplicationId;
    private ApplicantId applicantId;
    private Decision decision;

    public static ApplicantCreditValidatedEvent approved(LCApplicationId lcApplicationId, ApplicantId applicantId) {
        return new ApplicantCreditValidatedEvent(lcApplicationId, applicantId, Decision.accepted());
    }

    public static ApplicantCreditValidatedEvent rejected(LCApplicationId lcApplicationId, ApplicantId applicantId, String remarks) {
        return new ApplicantCreditValidatedEvent(lcApplicationId, applicantId, Decision.rejected(remarks));
    }
}
