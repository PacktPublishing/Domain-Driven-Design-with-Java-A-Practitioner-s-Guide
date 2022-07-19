package com.premonition.lc.ch06.domain.events;

import com.premonition.lc.ch06.domain.AdvisingBank;
import com.premonition.lc.ch06.domain.LCApplicationId;
import lombok.Data;

@Data
public class AdvisingBankChangedEvent {
    private final LCApplicationId applicationId;
    private final AdvisingBank advisingBank;

}
