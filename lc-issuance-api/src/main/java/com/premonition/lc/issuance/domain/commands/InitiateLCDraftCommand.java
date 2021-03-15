package com.premonition.lc.issuance.domain.commands;

import java.util.UUID;

public class InitiateLCDraftCommand {
    private UUID id;

    public InitiateLCDraftCommand(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
