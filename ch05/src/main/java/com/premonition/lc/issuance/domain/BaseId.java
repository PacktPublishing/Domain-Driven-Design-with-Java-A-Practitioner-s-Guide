package com.premonition.lc.issuance.domain;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;


@Data
public class BaseId implements Serializable {
    @NotNull
    private final UUID id;

    protected BaseId(UUID id) {
        this.id = id;
    }
}
