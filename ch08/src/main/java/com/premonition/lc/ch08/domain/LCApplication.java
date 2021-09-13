package com.premonition.lc.ch08.domain;

import com.premonition.lc.ch08.domain.commands.ChangeLCAmountCommand;
import com.premonition.lc.ch08.domain.commands.ChangeMerchandiseCommand;
import com.premonition.lc.ch08.domain.commands.StartNewLCApplicationCommand;
import com.premonition.lc.ch08.domain.commands.SubmitLCApplicationCommand;
import com.premonition.lc.ch08.domain.events.*;
import com.premonition.lc.ch08.domain.exceptions.LCAmountInvalidException;
import com.premonition.lc.ch08.domain.exceptions.LCAmountMissingException;
import com.premonition.lc.ch08.domain.exceptions.LCApplicationAlreadySubmittedException;
import com.premonition.lc.ch08.domain.exceptions.LCMerchandiseMissingException;
import lombok.extern.log4j.Log4j2;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.common.Assert;
import org.axonframework.deadline.DeadlineManager;
import org.axonframework.deadline.annotation.DeadlineHandler;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.javamoney.moneta.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import javax.money.MonetaryAmount;
import java.time.Duration;
import java.util.Objects;

@Aggregate
@Log4j2
public class LCApplication {

    public static final String LC_APPROVAL_DEADLINE = "lcApprovalDeadline";
    @AggregateIdentifier
    private LCApplicationId id;
    private LCState state;
    private MonetaryAmount amount;
    private Merchandise merchandise;

    @SuppressWarnings("unused")
    private LCApplication() {
        // Required by the framework
    }

    @CommandHandler
    public LCApplication(StartNewLCApplicationCommand command) {
        AggregateLifecycle.apply(new LCApplicationStartedEvent(command.getId(),
                command.getApplicantId(), command.getClientReference(), LCState.DRAFT));
    }

    private static void assertInDraft(LCState state) {
        Assert.assertThat(state, LCState::isDraft, LCApplicationAlreadySubmittedException::new);
    }

    private static void assertMerchandise(Merchandise merchandise) {
        Assert.assertThat(merchandise, Objects::nonNull, LCMerchandiseMissingException::new);
    }

    private static void assertPositive(MonetaryAmount amount) throws LCAmountMissingException {
        Assert.assertThat(amount, Objects::nonNull, LCAmountMissingException::new);
        Assert.assertThat(amount, LCApplication::isPositive, LCAmountInvalidException::new);
    }

    private static boolean isPositive(MonetaryAmount amount) {
        return amount.isGreaterThan(Money.of(0, amount.getCurrency()));
    }

    @EventSourcingHandler
    private void on(LCApplicationStartedEvent event) {
        this.id = event.getId();
        this.state = LCState.DRAFT;
    }

    @CommandHandler
    public void on(ChangeLCAmountCommand command) {
        assertInDraft(state);
        AggregateLifecycle.apply(new LCAmountChangedEvent(id, command.getAmount()));
    }

    @EventSourcingHandler
    void on(LCAmountChangedEvent event) {
        this.amount = event.getAmount();
    }

    @CommandHandler
    public void on(ChangeMerchandiseCommand command) {
        assertInDraft(state);
        AggregateLifecycle.apply(new MerchandiseChangedEvent(id, command.getMerchandise()));
    }

    @EventSourcingHandler
    private void on(MerchandiseChangedEvent event) {
        this.merchandise = event.getMerchandise();
    }

    @CommandHandler
    public void on(SubmitLCApplicationCommand command, DeadlineManager deadlineManager) {
        assertPositive(amount);
        assertMerchandise(merchandise);
        assertInDraft(state);
        AggregateLifecycle.apply(new LCApplicationSubmittedEvent(id, amount));
        deadlineManager.schedule(Duration.ofDays(30), LC_APPROVAL_DEADLINE, id);
    }

    @DeadlineHandler(deadlineName = LC_APPROVAL_DEADLINE)
    public void on(LCApplicationId lcApplicationId) {
        AggregateLifecycle.apply(new ApprovalDeadlineReachedEvent(lcApplicationId));
    }

    @EventHandler
    public void on(ApprovalDeadlineReachedEvent event) {
            log.info("***LC Approval Deadline Reached for LC id: " + event.getId());
        //Notify LC approvers to review the application and make decision
    }

    @EventSourcingHandler
    void on(LCApplicationSubmittedEvent event) {
        this.state = LCState.SUBMITTED;
    }
}
