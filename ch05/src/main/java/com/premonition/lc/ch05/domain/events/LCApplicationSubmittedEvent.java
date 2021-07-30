package com.premonition.lc.ch05.domain.events;

import com.premonition.lc.ch05.domain.LCApplicationId;

public class LCApplicationSubmittedEvent {
    private final LCApplicationId applicationId;

    public LCApplicationSubmittedEvent(LCApplicationId applicationId) {
        this.applicationId = applicationId;
    }
}
