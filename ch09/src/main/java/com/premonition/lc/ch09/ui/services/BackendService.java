package com.premonition.lc.ch09.ui.services;

import com.premonition.lc.ch09.domain.ApplicantId;
import com.premonition.lc.ch09.domain.LCApplicationId;
import com.premonition.lc.ch09.domain.queries.LCView;
import com.premonition.lc.ch09.domain.queries.MyDraftLCsQuery;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.premonition.lc.ch09.domain.commands.StartNewLCApplicationCommand.startApplication;

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

    public List<LCView> findMyDraftLCs(ApplicantId applicantId) {
        return queryGateway.query(new MyDraftLCsQuery(applicantId),
                        ResponseTypes.multipleInstancesOf(LCView.class))
                .join();

    }
}
