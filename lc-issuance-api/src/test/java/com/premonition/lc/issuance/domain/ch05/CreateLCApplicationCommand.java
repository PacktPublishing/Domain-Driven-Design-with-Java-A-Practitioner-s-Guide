package com.premonition.lc.issuance.domain.ch05;

import com.premonition.lc.issuance.domain.Country;
import lombok.Data;

@Data
public class CreateLCApplicationCommand {
    private LCApplicationId id;
    private Country beneficiaryCountry;

    public CreateLCApplicationCommand() {
        this.id = LCApplicationId.randomId();
    }

    public CreateLCApplicationCommand(LCApplicationId id, Country beneficiaryCountry) {
        this.id = id;
        this.beneficiaryCountry = beneficiaryCountry;
    }
}
