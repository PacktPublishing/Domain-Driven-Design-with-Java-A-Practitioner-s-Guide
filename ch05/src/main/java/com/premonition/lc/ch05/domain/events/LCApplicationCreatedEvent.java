package com.premonition.lc.ch05.domain.events;

import com.premonition.lc.ch05.domain.AdvisingBank;
import com.premonition.lc.ch05.domain.LCApplicationId;
import lombok.Data;

@Data
public class LCApplicationCreatedEvent {
    private final LCApplicationId id;
    private AdvisingBank advisingBank;

    public LCApplicationCreatedEvent(LCApplicationId id) {
        this.id = id;
    }

    public LCApplicationCreatedEvent(LCApplicationId id, AdvisingBank advisingBank) {
        this.id = id;
        this.advisingBank = advisingBank;
    }
}
