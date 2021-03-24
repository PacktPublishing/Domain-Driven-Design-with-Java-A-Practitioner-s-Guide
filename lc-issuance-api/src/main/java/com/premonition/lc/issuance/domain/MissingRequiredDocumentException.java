package com.premonition.lc.issuance.domain;

public class MissingRequiredDocumentException extends DomainException {
    public MissingRequiredDocumentException(String message) {
        super(message);
    }
}
