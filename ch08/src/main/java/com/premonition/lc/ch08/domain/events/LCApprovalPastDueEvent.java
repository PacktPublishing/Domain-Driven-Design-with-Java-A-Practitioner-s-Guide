package com.premonition.lc.ch08.domain.events;

import com.premonition.lc.ch08.domain.LCApplicationId;
import lombok.Data;

@Data
public class LCApprovalPastDueEvent {
    private final LCApplicationId id;
}
