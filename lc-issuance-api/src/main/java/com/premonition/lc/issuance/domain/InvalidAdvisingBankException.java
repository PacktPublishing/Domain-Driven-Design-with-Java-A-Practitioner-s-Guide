package com.premonition.lc.issuance.domain;

public class InvalidAdvisingBankException extends DomainException {

    public InvalidAdvisingBankException(Country country) {
        super("Advising bank has to be in country of beneficiary '" + country + "'.");
    }
}
