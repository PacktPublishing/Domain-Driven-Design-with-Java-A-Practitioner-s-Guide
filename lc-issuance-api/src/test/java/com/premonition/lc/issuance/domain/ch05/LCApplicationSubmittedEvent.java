package com.premonition.lc.issuance.domain.ch05;

public class LCApplicationSubmittedEvent {
    private final LCApplicationId applicationId;

    public LCApplicationSubmittedEvent(LCApplicationId applicationId) {
        this.applicationId = applicationId;
    }
}
