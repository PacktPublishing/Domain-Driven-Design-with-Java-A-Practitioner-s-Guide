package com.premonition.lc.issuance.domain;

public class DocumentNotAttachedException extends DomainException {
    public DocumentNotAttachedException(Document document) {
        super("Document of type " + document.getType() + " is not attached. " +
                "Please attach it first before trying to detach!");
    }
}
