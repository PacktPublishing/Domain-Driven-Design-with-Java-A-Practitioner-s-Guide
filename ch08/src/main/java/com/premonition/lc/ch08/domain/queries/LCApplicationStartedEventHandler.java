package com.premonition.lc.ch08.domain.queries;

import com.premonition.lc.ch08.domain.events.LCApplicationStartedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@RequiredArgsConstructor
public class LCApplicationStartedEventHandler {

    private final LCViewRepository repository;

    @EventHandler
    public void on(LCApplicationStartedEvent event) {
        final LCView lcView = new LCView(event.getId(), event.getApplicantId(),
                event.getClientReference(), event.getState());
        repository.save(lcView);
        log.info("New LC with client reference '{}' started!", event.getClientReference());
    }

}
