package com.premonition.lc.issuance.domain;

public class InvalidBeneficiaryCountryException extends DomainException {
    public InvalidBeneficiaryCountryException(Country country) {
        super("Cannot import from same country '" + country.getCode() + "'.");
    }
}
