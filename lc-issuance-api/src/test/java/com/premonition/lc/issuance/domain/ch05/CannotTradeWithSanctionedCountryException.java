package com.premonition.lc.issuance.domain.ch05;

import com.premonition.lc.issuance.domain.DomainException;

public class CannotTradeWithSanctionedCountryException extends DomainException {
    public CannotTradeWithSanctionedCountryException() {
        super("Import from this country is currently prohibited!");
    }
}
