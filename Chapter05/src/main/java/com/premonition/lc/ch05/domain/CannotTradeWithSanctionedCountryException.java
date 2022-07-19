package com.premonition.lc.ch05.domain;

public class CannotTradeWithSanctionedCountryException extends DomainException {
    public CannotTradeWithSanctionedCountryException() {
        super("Import from this country is currently prohibited!");
    }
}
