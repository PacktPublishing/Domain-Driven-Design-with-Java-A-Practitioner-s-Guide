package com.premonition.lc.issuance.domain;

import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@Data
public class Party {

    @NotBlank
    private final String name;
    @NotNull
    @Valid
    private final Address address;
}
