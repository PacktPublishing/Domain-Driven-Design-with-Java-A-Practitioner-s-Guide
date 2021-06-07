package com.premonition.lc.issuance.domain.events;

import com.premonition.lc.issuance.domain.AdvisingBank;
import com.premonition.lc.issuance.domain.LCApplicationId;
import lombok.Data;

@Data
public class AdvisingBankChangedEvent {
    private final LCApplicationId applicationId;
    private final AdvisingBank advisingBank;

}
