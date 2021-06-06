package com.premonition.lc.issuance.domain.events;

import com.premonition.lc.issuance.domain.LCApplicationId;

public class LCApplicationSubmittedEvent {
    private final LCApplicationId applicationId;

    public LCApplicationSubmittedEvent(LCApplicationId applicationId) {
        this.applicationId = applicationId;
    }
}
