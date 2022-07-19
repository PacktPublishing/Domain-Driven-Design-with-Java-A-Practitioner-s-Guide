package com.premonition.lc.ch08.ui.models;

import com.premonition.lc.ch08.domain.LCApplicationId;
import lombok.Data;

@Data
public class LCDetailsModel {
    private final LCApplicationId lcApplicationId;
    private final String clientReference;
}
