package com.premonition.lc.issuance.domain.ch05;

import com.premonition.lc.issuance.domain.DomainException;

public class AlreadySubmittedException extends DomainException {
    public AlreadySubmittedException(String message) {
        super(message);
    }
}
