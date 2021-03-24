package com.premonition.lc.issuance.domain.events;

import com.premonition.lc.issuance.domain.DocumentClause;
import com.premonition.lc.issuance.domain.LCId;
import lombok.Data;

@Data
public class DocumentClauseRemovedEvent {
    private final LCId id;
    private final DocumentClause documentClause;

    public DocumentClauseRemovedEvent(LCId id, DocumentClause documentClause) {
        this.id = id;
        this.documentClause = documentClause;
    }
}
