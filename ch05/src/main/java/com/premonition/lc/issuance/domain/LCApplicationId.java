package com.premonition.lc.issuance.domain;

import lombok.Data;

import java.util.UUID;

@Data
public class LCApplicationId {
    private final UUID id;

    public static LCApplicationId randomId() {
        return new LCApplicationId(UUID.randomUUID());
    }
}
