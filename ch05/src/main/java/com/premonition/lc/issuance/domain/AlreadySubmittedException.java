package com.premonition.lc.issuance.domain;

public class AlreadySubmittedException extends DomainException {
    public AlreadySubmittedException(String message) {
        super(message);
    }
}
