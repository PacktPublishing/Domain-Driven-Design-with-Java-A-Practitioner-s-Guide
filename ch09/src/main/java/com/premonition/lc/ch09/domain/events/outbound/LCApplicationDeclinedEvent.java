package com.premonition.lc.ch09.domain.events.outbound;

import com.premonition.lc.ch09.domain.LCApplicationId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LCApplicationDeclinedEvent {
    private LCApplicationId lcApplicationId;
}
