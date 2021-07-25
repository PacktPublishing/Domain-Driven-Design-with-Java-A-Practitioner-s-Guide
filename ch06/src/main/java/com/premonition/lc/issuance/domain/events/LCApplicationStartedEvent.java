package com.premonition.lc.issuance.domain.events;

import com.premonition.lc.issuance.domain.AdvisingBank;
import com.premonition.lc.issuance.domain.LCApplicationId;
import lombok.Data;

@Data
public class LCApplicationStartedEvent {
    private final LCApplicationId id;
    private final String applicantId;
    private final String clientReference;
    private AdvisingBank advisingBank;


    public LCApplicationStartedEvent(LCApplicationId id, String applicantId, String clientReference) {
        this.id = id;
        this.applicantId = applicantId;
        this.clientReference = clientReference;
    }
}
