package com.premonition.lc.ch07.ui.services;

import com.premonition.lc.ch07.domain.ApplicantId;
import com.premonition.lc.ch07.domain.LCApplicationId;
import com.premonition.lc.ch07.domain.commands.SaveLCApplicationCommand;
import com.premonition.lc.ch07.domain.queries.LCView;
import com.premonition.lc.ch07.domain.queries.MyDraftLCsQuery;
import com.premonition.lc.ch07.ui.models.LCDetailsModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.premonition.lc.ch07.domain.commands.StartNewLCApplicationCommand.startApplication;

@Service
public class BackendService {

    private final QueryGateway queryGateway;
    private final CommandGateway commandGateway;

    public BackendService(QueryGateway queryGateway, CommandGateway gateway) {
        this.queryGateway = queryGateway;
        this.commandGateway = gateway;
    }

    public LCApplicationId startNewLC(ApplicantId applicantId, String clientReference) {
        return commandGateway.sendAndWait(startApplication(applicantId, clientReference));
    }

    public void saveLC(LCDetailsModel model) {
        commandGateway.sendAndWait(new SaveLCApplicationCommand(model.getLcApplicationId()));
    }

    public List<LCView> findMyDraftLCs(ApplicantId applicantId) {
        return queryGateway.query(new MyDraftLCsQuery(applicantId),
                        ResponseTypes.multipleInstancesOf(LCView.class))
                .join();

    }
}
