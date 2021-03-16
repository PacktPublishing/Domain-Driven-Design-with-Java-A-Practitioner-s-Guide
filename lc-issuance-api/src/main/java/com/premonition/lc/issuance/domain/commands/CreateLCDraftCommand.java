package com.premonition.lc.issuance.domain.commands;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.money.MonetaryAmount;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
public class CreateLCDraftCommand {
    private UUID id;

    @NotBlank
    private String applicant;
    @NotBlank
    private String beneficiary;
    @Future
    private LocalDate issueDate;
    @Positive
    private MonetaryAmount lcAmount;
    @NotBlank
    private String merchandise;
    @NotBlank
    private String advisingBank;

    public CreateLCDraftCommand(UUID id) {
        this.id = id;
    }
}
