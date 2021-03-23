package com.premonition.lc.issuance.domain.events;

import com.premonition.lc.issuance.domain.Document;
import com.premonition.lc.issuance.domain.LCId;
import lombok.Data;
import org.axonframework.eventhandling.EventMessage;

@Data
public class DocumentDetachedEvent {
    private final LCId id;
    private final Document document;

    public DocumentDetachedEvent(LCId id, Document document) {
        this.id = id;
        this.document = document;
    }
}
