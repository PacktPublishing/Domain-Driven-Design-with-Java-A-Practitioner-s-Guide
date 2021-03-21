package com.premonition.lc.issuance.domain;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@Data
public class Address {
    @NotBlank
    private String line1;
    private String line2;
    private String line3;
    @NotBlank
    private String city;
    private String state;
    @NotBlank
    private String postalCode;
    @NotNull
    private Country country;
}
