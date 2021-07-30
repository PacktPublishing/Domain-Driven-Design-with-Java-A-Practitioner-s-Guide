package com.premonition.lc.ch07.domain.events;

import com.premonition.lc.ch07.domain.AdvisingBank;
import com.premonition.lc.ch07.domain.LCApplicationId;
import lombok.Data;

@Data
public class AdvisingBankChangedEvent {
    private final LCApplicationId applicationId;
    private final AdvisingBank advisingBank;

}
