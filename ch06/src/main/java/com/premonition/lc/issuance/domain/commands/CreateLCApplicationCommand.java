package com.premonition.lc.issuance.domain.commands;

import com.premonition.lc.issuance.domain.LCApplicationId;
import lombok.Data;

@Data
public class CreateLCApplicationCommand {
    private LCApplicationId id;
    private String clientReference;

    public CreateLCApplicationCommand(String clientReference) {
        this.id = LCApplicationId.randomId();
        this.clientReference = clientReference;
    }
}
