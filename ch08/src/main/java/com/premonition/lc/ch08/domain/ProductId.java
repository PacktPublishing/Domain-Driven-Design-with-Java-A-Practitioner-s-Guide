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
public class ProductId implements Serializable {

    private UUID id;

    public static ProductId randomId() {
        return new ProductId(UUID.randomUUID());
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
