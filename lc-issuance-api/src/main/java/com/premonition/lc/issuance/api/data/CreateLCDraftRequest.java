package com.premonition.lc.issuance.api.data;

import com.premonition.lc.issuance.domain.AdvisingBank;
import com.premonition.lc.issuance.domain.LCId;
import com.premonition.lc.issuance.domain.Party;
import com.premonition.lc.issuance.domain.commands.CreateLCDraftCommand;
import lombok.Data;

import javax.money.MonetaryAmount;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;


@Data
public class CreateLCDraftRequest {
    @NotNull
    @Valid
    private Party applicant;
    @NotNull
    @Valid
    private Party beneficiary;
    @Future
    private LocalDate issueDate;
    @Positive
    private MonetaryAmount amount;
    @NotBlank
    private String merchandise;
    @NotNull
    @Valid
    private AdvisingBank advisingBank;

    public CreateLCDraftCommand command(LCId id) {
        return CreateLCDraftCommand.builder()
                .id(id)
                .beneficiary(beneficiary)
                .applicant(applicant)
                .advisingBank(advisingBank)
                .issueDate(issueDate)
                .amount(amount)
                .merchandise(merchandise)
                .build();
    }
}
