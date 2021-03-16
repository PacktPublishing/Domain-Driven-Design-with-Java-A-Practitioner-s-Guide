package com.premonition.lc.issuance.domain.events;

import lombok.Data;

import javax.money.MonetaryAmount;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class LCDraftCreatedEvent {
    private final UUID id;

    private String applicant;
    private String beneficiary;
    private LocalDate issueDate;
    private MonetaryAmount lcAmount;
    private String merchandise;
    private String advisingBank;

    public LCDraftCreatedEvent(UUID id) {
        this.id = id;
    }
}
