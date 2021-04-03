package com.premonition.lc.issuance.api;

import com.premonition.lc.issuance.api.data.CreateLCDraftRequest;
import com.premonition.lc.issuance.domain.LCId;
import lombok.SneakyThrows;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.created;

@RestController
@RequestMapping(LCDraftController.RESOURCE)
public class LCDraftController {

    public static final String RESOURCE = "/lc-drafts";
    private final CommandGateway gateway;

    public LCDraftController(CommandGateway gateway) {
        this.gateway = gateway;
    }

    @PostMapping
    ResponseEntity<?> initiate(@RequestBody CreateLCDraftRequest request) {
        LCId id = LCId.from(UUID.randomUUID());
        gateway.sendAndWait(request.command(id));
        return created(toUri(id)).build();
    }

    @SneakyThrows
    private URI toUri(LCId id) {
        return new URI(RESOURCE + "/" + id);
    }
}
