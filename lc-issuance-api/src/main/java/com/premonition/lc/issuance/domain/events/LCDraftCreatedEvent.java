package com.premonition.lc.issuance.domain.events;

import com.premonition.lc.issuance.domain.LCId;
import lombok.Data;

import javax.money.MonetaryAmount;
import java.time.LocalDate;

@Data
public class LCDraftCreatedEvent {
    private final LCId id;

    private String applicant;
    private String beneficiary;
    private LocalDate issueDate;
    private MonetaryAmount lcAmount;
    private String merchandise;
    private String advisingBank;

    public LCDraftCreatedEvent(LCId id) {
        this.id = id;
    }
}
