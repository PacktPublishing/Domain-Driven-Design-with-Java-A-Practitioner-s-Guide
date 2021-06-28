package com.premonition.lc.issuance.ui.models;

import com.premonition.lc.issuance.domain.LCApplicationId;
import lombok.Data;

@Data
public class LCDetailsModel {
    private final LCApplicationId lcApplicationId;
    private final String clientReference;
}
