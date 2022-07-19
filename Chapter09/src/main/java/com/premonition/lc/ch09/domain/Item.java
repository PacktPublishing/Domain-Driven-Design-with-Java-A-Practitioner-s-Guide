package com.premonition.lc.ch09.domain;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class Item {
    @NotNull
    private ProductId productId;
    @Min(1)
    private int quantity;
}
