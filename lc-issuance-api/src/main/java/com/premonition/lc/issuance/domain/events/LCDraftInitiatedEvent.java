package com.premonition.lc.issuance.domain.events;

import java.util.UUID;

public class LCDraftInitiatedEvent {
    private final UUID id;

    public LCDraftInitiatedEvent(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
