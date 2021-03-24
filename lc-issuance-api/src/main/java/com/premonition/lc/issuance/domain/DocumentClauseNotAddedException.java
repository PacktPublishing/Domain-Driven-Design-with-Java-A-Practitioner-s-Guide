package com.premonition.lc.issuance.domain;

public class DocumentClauseNotAddedException extends DomainException {
    public DocumentClauseNotAddedException() {
        super("Document clause of that type is not attached." +
                "Please attach it first before trying to detach!");
    }
}
