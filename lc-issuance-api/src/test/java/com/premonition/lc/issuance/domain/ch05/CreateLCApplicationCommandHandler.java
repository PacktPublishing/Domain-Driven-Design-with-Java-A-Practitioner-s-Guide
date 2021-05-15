package com.premonition.lc.issuance.domain.ch05;

import java.util.Set;
import com.premonition.lc.issuance.domain.Country;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.modelling.command.Repository;

public class CreateLCApplicationCommandHandler {
    private final Repository<LCApplication> repository;                                // <1>
    private final Set<Country> sanctionedCountries;                                    // <2>

    public CreateLCApplicationCommandHandler(Repository<LCApplication> repository,
                                             Set<Country> sanctionedCountries) {
        this.repository = repository;
        this.sanctionedCountries = sanctionedCountries;
    }

    @CommandHandler
    public void handle(CreateLCApplicationCommand command) throws Exception {
        // Validations can be performed here as well
        repository.newInstance(() -> new LCApplication(command, sanctionedCountries)); // <3>
    }
}
