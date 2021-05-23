package com.premonition.lc.issuance.domain.ch05;

import com.premonition.lc.issuance.domain.AdvisingBank;
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
