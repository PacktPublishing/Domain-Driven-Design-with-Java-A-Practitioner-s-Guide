package com.premonition.lc.issuance.domain.ch05;

import com.premonition.lc.issuance.domain.AdvisingBank;
import lombok.Data;
import org.axonframework.eventhandling.EventMessage;

@Data
public class AdvisingBankChangedEvent {
    private final LCApplicationId applicationId;
    private final AdvisingBank advisingBank;

}
