package com.premonition.lc.ch08.domain;

import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
@Builder
public class Merchandise {
    @NotEmpty
    @Builder.Default
    @Valid
    private Set<Item> items = Set.of();
    private String description;
}
