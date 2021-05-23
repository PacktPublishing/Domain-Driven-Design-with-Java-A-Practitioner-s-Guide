package com.premonition.lc.issuance.domain.ch05;

import java.util.Set;
import com.premonition.lc.issuance.domain.Country;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.modelling.command.Repository;
import org.springframework.stereotype.Service;

@Service
public class CreateLCApplicationCommandHandler {
    private final Repository<LCApplication> repository;
    private final Set<Country> sanctionedCountries;

    public CreateLCApplicationCommandHandler(Repository<LCApplication> repository,
                                             Set<Country> sanctionedCountries) {
        this.repository = repository;
        this.sanctionedCountries = sanctionedCountries;
    }

    @CommandHandler
    public void handle(CreateLCApplicationCommand command) throws Exception {
        // Validations can be performed here as well
        repository.newInstance(() -> new LCApplication(command, sanctionedCountries));
    }
}
