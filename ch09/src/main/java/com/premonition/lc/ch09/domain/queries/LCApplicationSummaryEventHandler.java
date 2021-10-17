package com.premonition.lc.ch09.domain.queries;

import com.premonition.lc.ch09.domain.events.outbound.LCApplicationStartedEvent;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LCApplicationSummaryEventHandler {

    private final LCSummaryRepository repository;

    @EventHandler
    public void on(LCApplicationStartedEvent event) {
        final List<LCSummaryView> all = repository.findAll();
        final LCSummaryView view = all.stream()
                .findFirst()
                .orElse(new LCSummaryView());
        repository.save(view.increment());
    }

}
