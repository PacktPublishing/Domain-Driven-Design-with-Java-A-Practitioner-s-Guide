package com.premonition.lc.ch06.domain;

import lombok.Data;

import java.util.UUID;

@Data
public class LCApplicationId {
    private final UUID id;

    public static LCApplicationId randomId() {
        return new LCApplicationId(UUID.randomUUID());
    }
}
