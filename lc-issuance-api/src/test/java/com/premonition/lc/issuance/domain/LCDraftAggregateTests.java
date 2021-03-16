package com.premonition.lc.issuance.domain;

import com.premonition.lc.issuance.domain.commands.CreateLCDraftCommand;
import com.premonition.lc.issuance.domain.events.LCDraftCreatedEvent;
import org.axonframework.messaging.interceptors.BeanValidationInterceptor;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static javax.money.Monetary.getCurrency;

public class LCDraftAggregateTests {

    private FixtureConfiguration<LCDraft> fixture;

    @BeforeEach
    void setUp() {
        fixture = new AggregateTestFixture<>(LCDraft.class)
                .registerCommandDispatchInterceptor(new BeanValidationInterceptor<>());
    }

    @Test
    void shouldPublishDraftInitiatedEvent() {
        final UUID id = UUID.randomUUID();
        CreateLCDraftCommand command = CreateLCDraftCommand.builder()
                .id(id)
                .applicant("Applicant")
                .beneficiary("Exporter")
                .advisingBank("My Awesome Bank")
                .issueDate(LocalDate.now().plusDays(10))
                .lcAmount(Money.of(100, getCurrency("USD")))
                .merchandise("Lindt Milk Chocolates")
                .build();
        fixture.given()
                .when(command)
                .expectEvents(new LCDraftCreatedEvent(id));
    }
}
