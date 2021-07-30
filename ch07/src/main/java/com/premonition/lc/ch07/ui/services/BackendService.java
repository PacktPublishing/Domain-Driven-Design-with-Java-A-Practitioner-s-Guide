package com.premonition.lc.ch07.ui.services;

import com.premonition.lc.ch07.domain.LCApplicationId;
import com.premonition.lc.ch07.domain.commands.SaveLCApplicationCommand;
import com.premonition.lc.ch07.ui.models.LCDetailsModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import static com.premonition.lc.ch07.domain.commands.StartNewLCApplicationCommand.startApplication;

@Service
public class BackendService {

    private final CommandGateway gateway;

    public BackendService(CommandGateway gateway) {
        this.gateway = gateway;
    }

    public LCApplicationId startNewLC(String applicantId, String clientReference) {
        return gateway.sendAndWait(startApplication(applicantId, clientReference));
    }

    public void saveLC(LCDetailsModel model) {
        gateway.sendAndWait(new SaveLCApplicationCommand(model.getLcApplicationId()));
    }
}
