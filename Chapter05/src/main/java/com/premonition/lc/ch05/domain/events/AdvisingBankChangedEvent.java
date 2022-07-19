package com.premonition.lc.ch05.domain.events;

import com.premonition.lc.ch05.domain.AdvisingBank;
import com.premonition.lc.ch05.domain.LCApplicationId;
import lombok.Data;

@Data
public class AdvisingBankChangedEvent {
    private final LCApplicationId applicationId;
    private final AdvisingBank advisingBank;

}
