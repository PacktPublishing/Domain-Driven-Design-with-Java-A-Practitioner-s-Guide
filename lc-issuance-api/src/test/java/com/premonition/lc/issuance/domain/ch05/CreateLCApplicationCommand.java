package com.premonition.lc.issuance.domain.ch05;

import com.premonition.lc.issuance.domain.Country;
import lombok.Data;

@Data
public class CreateLCApplicationCommand {
    private LCApplicationId id;

    public CreateLCApplicationCommand() {
        this.id = LCApplicationId.randomId();
    }

    public Country getBeneficiaryCountry() {
        return null;
    }
}
