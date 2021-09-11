package com.premonition.lc.ch08.domain;

import com.premonition.lc.ch08.domain.commands.ChangeLCAmountCommand;
import com.premonition.lc.ch08.domain.commands.ChangeMerchandiseCommand;
import com.premonition.lc.ch08.domain.commands.StartNewLCApplicationCommand;
import com.premonition.lc.ch08.domain.commands.SubmitLCApplicationCommand;
import com.premonition.lc.ch08.domain.events.LCAmountChangedEvent;
import com.premonition.lc.ch08.domain.events.LCApplicationStartedEvent;
import com.premonition.lc.ch08.domain.events.LCApplicationSubmittedEvent;
import com.premonition.lc.ch08.domain.events.MerchandiseChangedEvent;
import com.premonition.lc.ch08.domain.exceptions.LCAmountInvalidException;
import com.premonition.lc.ch08.domain.exceptions.LCAmountMissingException;
import com.premonition.lc.ch08.domain.exceptions.LCApplicationAlreadySubmittedException;
import com.premonition.lc.ch08.domain.exceptions.LCMerchandiseMissingException;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.common.Assert;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.javamoney.moneta.Money;

import javax.money.MonetaryAmount;
import java.util.Objects;

@Aggregate
public class LCApplication {

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
    public void on(SubmitLCApplicationCommand command)  {
        assertPositive(amount);
        assertMerchandise(merchandise);
        assertInDraft(state);
        AggregateLifecycle.apply(new LCApplicationSubmittedEvent(id));
    }

    @EventSourcingHandler
    void on(LCApplicationSubmittedEvent event) {
        this.state = LCState.SUBMITTED;
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
}
