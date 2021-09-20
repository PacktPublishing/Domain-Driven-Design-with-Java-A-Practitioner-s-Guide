package com.premonition.lc.ch08.domain.sagas;

import com.premonition.lc.ch08.domain.LCApplicationId;
import com.premonition.lc.ch08.domain.commands.ApproveLCApplicationCommand;
import com.premonition.lc.ch08.domain.events.outbound.LCApplicationApprovedEvent;
import com.premonition.lc.ch08.domain.events.outbound.LCApplicationSubmittedEvent;
import com.premonition.lc.ch08.domain.events.inbound.ApplicantCreditValidatedEvent;
import com.premonition.lc.ch08.domain.events.inbound.ProductLegalityValidatedEvent;
import com.premonition.lc.ch08.domain.events.inbound.ProductValueValidatedEvent;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.javamoney.moneta.Money;
import org.springframework.beans.factory.annotation.Autowired;

import javax.money.Monetary;
import javax.money.MonetaryAmount;

@Saga
public class AutoApprovalSaga {

    private static final MonetaryAmount AUTO_APPROVAL_THRESHOLD
            = Money.of(10000, Monetary.getCurrency("USD"));
    private boolean productValueValidated;
    private boolean productLegalityValidated;
    private boolean applicantValidated;
    private MonetaryAmount amount;
    @Autowired
    private transient CommandGateway gateway;


    @StartSaga
    @SagaEventHandler(associationProperty = "lcApplicationId")
    public void on(LCApplicationSubmittedEvent event) {
        amount = event.getAmount();
    }

    @SagaEventHandler(associationProperty = "lcApplicationId")
    public void on(ApplicantCreditValidatedEvent event) {
        if (!event.getDecision().isValid()) {
            SagaLifecycle.end();
        } else {
            applicantValidated = true;
            autoApproveOrEnd(event.getLcApplicationId());
        }
    }

    @SagaEventHandler(associationProperty = "lcApplicationId")
    public void on(ProductValueValidatedEvent event) {
        if (!event.getDecision().isValid()) {
            SagaLifecycle.end();
        } else {
            productValueValidated = true;
            autoApproveOrEnd(event.getLcApplicationId());
        }
    }

    @SagaEventHandler(associationProperty = "lcApplicationId")
    public void on(ProductLegalityValidatedEvent event) {
        if (!event.getDecision().isValid()) {
            SagaLifecycle.end();
        } else {
            productLegalityValidated = true;
            autoApproveOrEnd(event.getLcApplicationId());
        }
    }

    private void autoApproveOrEnd(LCApplicationId lcApplicationId) {
        if (productValueValidated && applicantValidated && productLegalityValidated) {
            if (amount.isLessThanOrEqualTo(AUTO_APPROVAL_THRESHOLD)) {
                gateway.send(new ApproveLCApplicationCommand(lcApplicationId));
            } else {
                SagaLifecycle.end();
            }
        }
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "lcApplicationId")
    public void on(LCApplicationApprovedEvent event) {
    }
}
