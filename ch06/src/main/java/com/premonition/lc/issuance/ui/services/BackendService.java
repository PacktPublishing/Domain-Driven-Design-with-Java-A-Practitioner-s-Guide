package com.premonition.lc.issuance.ui.services;

import com.premonition.lc.issuance.domain.LCApplicationId;
import com.premonition.lc.issuance.domain.commands.CreateLCApplicationCommand;
import com.premonition.lc.issuance.domain.commands.SaveLCApplicationCommand;
import com.premonition.lc.issuance.ui.viewmodels.LCDetailsViewModel;
import lombok.extern.log4j.Log4j2;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class BackendService {

    private final CommandGateway gateway;

    public BackendService(CommandGateway gateway) {
        this.gateway = gateway;
    }

    public LCApplicationId createLC(String applicantId, String clientReference) {
        final LCApplicationId id = gateway.sendAndWait(new CreateLCApplicationCommand(applicantId, clientReference));
        log.info("Created LC with id: {}", id);
        return id;
    }

    public void saveLC(LCDetailsViewModel viewModel) {
        gateway.sendAndWait(new SaveLCApplicationCommand(viewModel.getApplicationId()));
    }
}
