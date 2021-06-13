package com.premonition.lc.issuance.ui.services;

import com.premonition.lc.issuance.domain.Country;
import com.premonition.lc.issuance.domain.LCApplicationId;
import com.premonition.lc.issuance.domain.commands.CreateLCApplicationCommand;
import com.premonition.lc.issuance.ui.events.LCCreatedEvent;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import lombok.extern.log4j.Log4j2;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class CreateLCService {

    private final CommandGateway gateway;

    public CreateLCService(CommandGateway gateway) {
        this.gateway = gateway;
    }

    public LCApplicationId createLC(String clientReference) {
        final LCApplicationId id = gateway.sendAndWait(new CreateLCApplicationCommand(clientReference));
        log.info("Created LC with id: {}", id);
        return id;
    }
}
