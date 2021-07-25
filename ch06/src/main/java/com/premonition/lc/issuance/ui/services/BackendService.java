package com.premonition.lc.issuance.ui.services;

import com.premonition.lc.issuance.domain.LCApplicationId;
import com.premonition.lc.issuance.domain.commands.StartNewLCApplicationCommand;
import com.premonition.lc.issuance.domain.commands.SaveLCApplicationCommand;
import com.premonition.lc.issuance.ui.models.LCDetailsModel;
import lombok.extern.log4j.Log4j2;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import static com.premonition.lc.issuance.domain.commands.StartNewLCApplicationCommand.startApplication;

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
