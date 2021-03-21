package com.premonition.lc.issuance.domain;

import java.util.UUID;

public class ClientId extends BaseId {
    public ClientId(UUID id) {
        super(id);
    }

    public static ClientId from(UUID id) {
        return new ClientId(id);
    }
}
