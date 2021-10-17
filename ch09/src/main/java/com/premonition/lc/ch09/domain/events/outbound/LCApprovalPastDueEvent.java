package com.premonition.lc.ch09.domain.events.outbound;

import com.premonition.lc.ch09.domain.LCApplicationId;
import lombok.Data;

@Data
public class LCApprovalPastDueEvent {
    private final LCApplicationId id;
}
