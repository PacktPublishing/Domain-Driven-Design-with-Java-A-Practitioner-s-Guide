package com.premonition.lc.issuance.domain.commands;

import com.premonition.lc.issuance.domain.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.money.MonetaryAmount;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
public class CreateLCDraftCommand {

    @NotNull
    private LCId id;

    @NotNull
    private ClientId clientId;
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

    public Country countryOfApplicant() {
        return applicant.getAddress().getCountry();
    }

    public Country countryOfBeneficiary() {
        return beneficiary.getAddress().getCountry();
    }

    public Country countryOfAdvisingBank() {
        return advisingBank.getCountry();
    }
}
