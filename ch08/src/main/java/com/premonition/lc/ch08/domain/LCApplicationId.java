package com.premonition.lc.ch08.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class LCApplicationId implements Serializable {
    private UUID id;

    public static LCApplicationId randomId() {
        return new LCApplicationId(UUID.randomUUID());
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
