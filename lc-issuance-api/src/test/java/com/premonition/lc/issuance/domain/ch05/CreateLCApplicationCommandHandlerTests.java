package com.premonition.lc.issuance.domain.ch05;

import com.premonition.lc.issuance.domain.Country;
import com.premonition.lc.issuance.domain.DomainException;
import org.axonframework.modelling.command.Repository;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.premonition.lc.issuance.domain.Country.SOKOVIA;
import static com.premonition.lc.issuance.domain.Country.WAKANDA;
import static com.premonition.lc.issuance.domain.ch05.LCApplicationId.randomId;

public class CreateLCApplicationCommandHandlerTests {
    private FixtureConfiguration<LCApplication> fixture;

    @BeforeEach
    void setUp() {
        final Set<Country> sanctioned = Set.of(SOKOVIA);
        fixture = new AggregateTestFixture<>(LCApplication.class);              // <1>

        final Repository<LCApplication> repository = fixture.getRepository();   // <2>

        CreateLCApplicationCommandHandler handler =
                new CreateLCApplicationCommandHandler(repository, sanctioned);  // <3>
        fixture.registerAnnotatedCommandHandler(handler);                       // <4>
    }

    @Test
    void shouldFailIfBeneficiaryCountryIsSanctioned() {
        fixture.given()
                .when(new CreateLCApplicationCommand(randomId(), SOKOVIA))
                .expectException(CannotTradeWithSanctionedCountryException.class);
    }

    @Test
    void shouldCreateIfCountryIsNotSanctioned() {
        final LCApplicationId applicationId = randomId();
        fixture.given()
                .when(new CreateLCApplicationCommand(applicationId, WAKANDA))
                .expectEvents(new LCApplicationCreatedEvent(applicationId));
    }
}
