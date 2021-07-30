package com.premonition.lc.ch05.domain;

public class AlreadySubmittedException extends DomainException {
    public AlreadySubmittedException(String message) {
        super(message);
    }
}
