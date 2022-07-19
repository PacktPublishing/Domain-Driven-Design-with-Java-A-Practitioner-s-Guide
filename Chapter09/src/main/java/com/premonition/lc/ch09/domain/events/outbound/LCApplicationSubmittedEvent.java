package com.premonition.lc.ch09.domain.events.outbound;

import com.premonition.lc.ch09.domain.LCApplicationId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.money.MonetaryAmount;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LCApplicationSubmittedEvent {
    private LCApplicationId lcApplicationId;
    private MonetaryAmount amount;
}
