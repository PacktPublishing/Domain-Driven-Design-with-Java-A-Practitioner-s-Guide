package com.premonition.lc.issuance.domain.events;

import com.premonition.lc.issuance.domain.LCId;
import lombok.Data;
import org.axonframework.eventhandling.EventMessage;

@Data
public class LCDraftSubmittedEvent {
    private final LCId id;

    public LCDraftSubmittedEvent(LCId id) {
        this.id = id;
    }
}
