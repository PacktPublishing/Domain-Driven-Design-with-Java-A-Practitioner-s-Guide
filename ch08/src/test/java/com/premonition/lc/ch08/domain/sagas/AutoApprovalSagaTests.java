package com.premonition.lc.ch08.domain.sagas;

import com.premonition.lc.ch08.domain.ApplicantId;
import com.premonition.lc.ch08.domain.LCApplicationId;
import com.premonition.lc.ch08.domain.ProductId;
import com.premonition.lc.ch08.domain.commands.ApproveLCApplicationCommand;
import com.premonition.lc.ch08.domain.events.inbound.ApplicantCreditValidatedEvent;
import com.premonition.lc.ch08.domain.events.inbound.ProductLegalityValidatedEvent;
import com.premonition.lc.ch08.domain.events.inbound.ProductValueValidatedEvent;
import com.premonition.lc.ch08.domain.events.outbound.LCApplicationApprovedEvent;
import com.premonition.lc.ch08.domain.events.outbound.LCApplicationSubmittedEvent;
import org.axonframework.test.saga.SagaTestFixture;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import java.util.List;
import java.util.stream.Stream;

import static com.premonition.lc.ch08.domain.sagas.AutoApprovalSaga.AUTO_APPROVAL_THRESHOLD;

@DisplayName("Auto approval saga")
class AutoApprovalSagaTests {

    private static final Money THOUSAND_DOLLARS = Money.of(1000,
            Monetary.getCurrency("USD"));
    private static final MonetaryAmount FIFTY_THOUSAND_DOLLARS = Money.of(50_000,
            Monetary.getCurrency("USD"));
    private static final Money ONE_DOLLAR = Money.of(1,
            Monetary.getCurrency("USD"));
    private SagaTestFixture<AutoApprovalSaga> fixture;

    @BeforeEach
    void setUp() {
        fixture = new SagaTestFixture<>(AutoApprovalSaga.class);
    }

    @Nested
    @DisplayName("life cycle")
    class LifeCycleTests {
        @DisplayName("should start when LC is submitted and amount is")
        @ParameterizedTest(name = "{1} auto approval threshold")
        @MethodSource("autoApprovalThreshold")
        void shouldStartSagaOnSubmit(MonetaryAmount amount, String thresholdForTestTitle) {
            final LCApplicationId lcApplicationId = LCApplicationId.randomId();
            fixture.givenAggregate(lcApplicationId.toString()).published()
                    .whenPublishingA(
                            new LCApplicationSubmittedEvent(lcApplicationId, amount))
                    .expectActiveSagas(1);
        }

        @DisplayName("should end when decision is rejected for")
        @ParameterizedTest(name = "{2}")
        @MethodSource("rejectedDecision")
        void shouldEndSagaOnRejectedDecision(LCApplicationId lcApplicationId, Object rejectionEvent, String typename) {

            fixture.givenAggregate(lcApplicationId.toString()).published(
                            new LCApplicationSubmittedEvent(lcApplicationId, THOUSAND_DOLLARS)
                    ).whenPublishingA(rejectionEvent)
                    .expectActiveSagas(0)
                    .expectNoDispatchedCommands();
        }

        @Test
        @DisplayName("should end immediately if amount is greater than auto approval threshold")
        void shouldEndSagaIfAmountGreaterThanAutoApprovalThreshold() {
            final LCApplicationId lcApplicationId = LCApplicationId.randomId();
            fixture.givenAggregate(lcApplicationId.toString()).published()
                    .whenPublishingA(
                            new LCApplicationSubmittedEvent(lcApplicationId, FIFTY_THOUSAND_DOLLARS))
                    .expectActiveSagas(0);
        }

        private static Stream<Arguments> rejectedDecision() {
            final LCApplicationId lcApplicationId = LCApplicationId.randomId();
            final ApplicantId applicantId = ApplicantId.randomId();
            final ProductId productId = ProductId.randomId();

            final ApplicantCreditValidatedEvent applicant = ApplicantCreditValidatedEvent.rejected(lcApplicationId,
                    applicantId, "Rejected!");
            final ProductLegalityValidatedEvent legality = ProductLegalityValidatedEvent.rejected(lcApplicationId,
                    productId, "Rejected!");
            final ProductValueValidatedEvent value = ProductValueValidatedEvent.rejected(lcApplicationId, productId,
                    "Rejected!");
            return Stream.of(
                    Arguments.of(lcApplicationId, applicant, eventTypeName(applicant)),
                    Arguments.of(lcApplicationId, legality, eventTypeName(legality)),
                    Arguments.of(lcApplicationId, value, eventTypeName(value))
            );
        }

        private static Stream<Arguments> autoApprovalThreshold() {
            return Stream.of(
                    Arguments.of(AUTO_APPROVAL_THRESHOLD.subtract(ONE_DOLLAR), "lesser than"),
                    Arguments.of(AUTO_APPROVAL_THRESHOLD, "equal to")
            );
        }
    }

    private static String eventTypeName(Object event) {
        return event.getClass().getSimpleName().replaceAll("([A-Z])", " $1").toLowerCase();
    }

    @ParameterizedTest(name = "{3}")
    @MethodSource("autoApprove")
    @DisplayName("should auto approve on")
    void shouldAutoApprove(LCApplicationId lcApplicationId, List<?> givenEvents, Object when, String eventTypeName) {
        fixture.givenAggregate(lcApplicationId.toString()).published(givenEvents.toArray())
                .whenPublishingA(when)
                .expectActiveSagas(1)
                .expectDispatchedCommands(new ApproveLCApplicationCommand(lcApplicationId));
    }

    @Test
    @DisplayName("should end saga after auto approval")
    void shouldEndSagaAfterAutoApproval() {
        final LCApplicationId lcApplicationId = LCApplicationId.randomId();
        final ApplicantId applicantId = ApplicantId.randomId();
        final ProductId productId = ProductId.randomId();

        final LCApplicationSubmittedEvent submitted = new LCApplicationSubmittedEvent(lcApplicationId, THOUSAND_DOLLARS);
        final ApplicantCreditValidatedEvent applicant = ApplicantCreditValidatedEvent.approved(lcApplicationId, applicantId);
        final ProductLegalityValidatedEvent legality = ProductLegalityValidatedEvent.approved(lcApplicationId, productId);
        final ProductValueValidatedEvent value = ProductValueValidatedEvent.approved(lcApplicationId, productId);

        fixture.givenAggregate(lcApplicationId.toString()).published(
                        submitted, applicant, legality, value)
                .whenPublishingA(new LCApplicationApprovedEvent(lcApplicationId))
                .expectActiveSagas(0)
                .expectNoDispatchedCommands();
    }

    private static Stream<Arguments> autoApprove() {
        final LCApplicationId lcApplicationId = LCApplicationId.randomId();
        final ApplicantId applicantId = ApplicantId.randomId();
        final ProductId productId = ProductId.randomId();

        final LCApplicationSubmittedEvent submitted = new LCApplicationSubmittedEvent(lcApplicationId, THOUSAND_DOLLARS);
        final ApplicantCreditValidatedEvent applicant = ApplicantCreditValidatedEvent.approved(lcApplicationId, applicantId);
        final ProductLegalityValidatedEvent legality = ProductLegalityValidatedEvent.approved(lcApplicationId, productId);
        final ProductValueValidatedEvent value = ProductValueValidatedEvent.approved(lcApplicationId, productId);

        return Stream.of(
                Arguments.of(lcApplicationId, List.of(submitted, applicant, legality), value, eventTypeName(value)),
                Arguments.of(lcApplicationId, List.of(submitted, applicant, value), legality, eventTypeName(legality)),
                Arguments.of(lcApplicationId, List.of(submitted, legality, value), applicant, eventTypeName(applicant))
        );
    }
}