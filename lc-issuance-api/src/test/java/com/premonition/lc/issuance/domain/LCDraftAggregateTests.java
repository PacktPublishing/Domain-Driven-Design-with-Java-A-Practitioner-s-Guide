package com.premonition.lc.issuance.domain;

import com.premonition.lc.issuance.domain.commands.AttachDocumentCommand;
import com.premonition.lc.issuance.domain.commands.CreateLCDraftCommand;
import com.premonition.lc.issuance.domain.commands.DetachDocumentCommand;
import com.premonition.lc.issuance.domain.commands.IssueLCCommand;
import com.premonition.lc.issuance.domain.events.DocumentAttachedEvent;
import com.premonition.lc.issuance.domain.events.DocumentDetachedEvent;
import com.premonition.lc.issuance.domain.events.LCDraftCreatedEvent;
import org.axonframework.messaging.interceptors.BeanValidationInterceptor;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.premonition.lc.issuance.domain.Country.INDIA;
import static com.premonition.lc.issuance.domain.Country.USA;
import static com.premonition.lc.issuance.domain.Document.Type.CERTIFICATE_OF_ORIGIN;
import static java.util.UUID.randomUUID;
import static javax.money.Monetary.getCurrency;

public class LCDraftAggregateTests {

    private FixtureConfiguration<LCDraft> fixture;
    private Party applicant;
    private Party beneficiary;
    private LCId id;

    @BeforeEach
    void setUp() {
        fixture = new AggregateTestFixture<>(LCDraft.class)
                .registerCommandDispatchInterceptor(new BeanValidationInterceptor<>());
        applicant = Party.builder()
                .address(Address.builder()
                        .line1("123 Awesome Street")
                        .country(USA)
                        .postalCode("10001")
                        .city("New York")
                        .build())
                .name("Amazing Importers")
                .build();
        beneficiary = Party.builder()
                .address(Address.builder()
                        .line1("123 Amazing Street")
                        .country(INDIA)
                        .postalCode("560048")
                        .city("Bengaluru")
                        .build())
                .name("Amazing Exporters")
                .build();
        id = LCId.from(randomUUID());
    }

    @Test
    void shouldPublishDraftInitiatedEvent() {
        CreateLCDraftCommand command = CreateLCDraftCommand.builder()
                .id(id)
                .applicant(applicant)
                .beneficiary(beneficiary)
                .advisingBank(AdvisingBank.builder()
                        .id(BankId.from(randomUUID()))
                        .country(INDIA)
                        .build())
                .issueDate(LocalDate.now().plusDays(10))
                .amount(Money.of(100, getCurrency("USD")))
                .merchandise("Lindt Milk Chocolates")
                .clientId(ClientId.from(randomUUID()))
                .build();
        fixture.given()
                .when(command)
                .expectEvents(new LCDraftCreatedEvent(id));
    }

    @Test
    void shouldNotImportFromCountryOfApplicant() {
        final Party localBeneficiary = Party.builder()
                .address(Address.builder()
                        .line1("123 Amazing Street")
                        .country(USA)
                        .postalCode("45249")
                        .city("Cincinnati")
                        .build())
                .name("Amazing Exporters")
                .build();
        CreateLCDraftCommand command = CreateLCDraftCommand.builder()
                .id(id)
                .applicant(applicant)
                .beneficiary(localBeneficiary)
                .advisingBank(AdvisingBank.builder()
                        .id(BankId.from(randomUUID()))
                        .country(INDIA)
                        .build())
                .issueDate(LocalDate.now().plusDays(10))
                .amount(Money.of(100, getCurrency("USD")))
                .merchandise("Lindt Milk Chocolates")
                .clientId(ClientId.from(randomUUID()))
                .build();

        fixture.givenNoPriorActivity()
                .when(command)
                .expectException(InvalidBeneficiaryCountryException.class)
                .expectNoEvents();
    }

    @Test
    void shouldHaveAdvisingBankInTheCountryOfBeneficiary() {
        CreateLCDraftCommand command = CreateLCDraftCommand.builder()
                .id(id)
                .applicant(applicant)
                .beneficiary(beneficiary)
                .advisingBank(AdvisingBank.builder()
                        .id(BankId.from(randomUUID()))
                        .country(USA)
                        .build())
                .issueDate(LocalDate.now().plusDays(10))
                .amount(Money.of(100, getCurrency("USD")))
                .merchandise("Lindt Milk Chocolates")
                .clientId(ClientId.from(randomUUID()))
                .build();

        fixture.given()
                .when(command)
                .expectException(InvalidAdvisingBankException.class)
                .expectNoEvents();
    }

    @Test
    void shouldNotBeAbleToIssueLCInDraftStatus() {
        fixture.given(new LCDraftCreatedEvent(id))
                .when(new IssueLCCommand(id))
                .expectException(DomainException.class)
                .expectNoEvents();
    }

    @Test
    void shouldAllowAttachingDocument() {
        final Document document = Document.standard(CERTIFICATE_OF_ORIGIN);
        fixture.given(new LCDraftCreatedEvent(id))
                .when(new AttachDocumentCommand(id, document))
                .expectEvents(new DocumentAttachedEvent(id, document));
    }

    @Test
    void shouldAllowDetachingDocument() {
        final Document document = Document.standard(CERTIFICATE_OF_ORIGIN);
        fixture.given(new LCDraftCreatedEvent(id), new DocumentAttachedEvent(id, document))
                .when(new DetachDocumentCommand(id, document))
                .expectEvents(new DocumentDetachedEvent(id, document));
    }

    @Test
    void shouldNotDetachDocumentThatWasNotPreviouslyAttached() {
        final Document document = Document.adhoc("Test");
        fixture.given(new LCDraftCreatedEvent(id))
                .when(new DetachDocumentCommand(id, document))
                .expectException(DocumentNotAttachedException.class)
                .expectNoEvents();
    }
}
