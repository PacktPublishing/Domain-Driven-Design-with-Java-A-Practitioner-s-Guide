package com.premonition.lc.issuance.domain;

import java.util.UUID;


public class BankId extends BaseId {
    protected BankId(UUID id) {
        super(id);
    }

    public static BankId from(UUID id) {
        return new BankId(id);
    }
}
