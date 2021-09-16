package com.premonition.lc.ch08.domain;

import com.premonition.lc.ch08.domain.commands.*;
import com.premonition.lc.ch08.domain.events.*;
import com.premonition.lc.ch08.domain.exceptions.*;
import lombok.extern.log4j.Log4j2;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.common.Assert;
import org.axonframework.deadline.DeadlineManager;
import org.axonframework.deadline.annotation.DeadlineHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.javamoney.moneta.Money;

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

    private boolean isSubmitted() {
        return state.isSubmitted();
    }

    private static void assertInSubmitted(LCState state) {
        Assert.assertThat(state, LCState::isSubmitted, LCApplicationNotInSubmittedStateException::new);
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
        deadlineManager.schedule(Duration.ofDays(30), LC_APPROVAL_DEADLINE, id);
        AggregateLifecycle.apply(new LCApplicationSubmittedEvent(id, amount));
    }

    @CommandHandler
    public void on(ApproveLCApplicationCommand command, DeadlineManager deadlineManager) {
        assertInSubmitted(state);
        deadlineManager.cancelAll(LC_APPROVAL_DEADLINE);
        AggregateLifecycle.apply(new LCApplicationApprovedEvent(id));
    }

    @CommandHandler
    public void on(DeclineLCApplicationCommand command, DeadlineManager deadlineManager) {
        assertInSubmitted(state);
        deadlineManager.cancelAll(LC_APPROVAL_DEADLINE);
        AggregateLifecycle.apply(new LCApplicationDeclinedEvent(id));
    }

    @DeadlineHandler(deadlineName = LC_APPROVAL_DEADLINE)
    public void onApprovalDeadline(LCApplicationId lcApplicationId) {
        if (isSubmitted()) {
            AggregateLifecycle.apply(new ApprovalDeadlineReachedEvent(lcApplicationId));
        }
    }

    @EventSourcingHandler
    void on(LCApplicationSubmittedEvent event) {
        this.state = LCState.SUBMITTED;
    }

    @EventSourcingHandler
    void on(LCApplicationApprovedEvent event) {
        this.state = LCState.APPROVED;
    }

    @EventSourcingHandler
    void on(LCApplicationDeclinedEvent event) {
        this.state = LCState.REJECTED;
    }
}
