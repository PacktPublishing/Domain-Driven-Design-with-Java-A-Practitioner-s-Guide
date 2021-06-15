package com.premonition.lc.issuance.ui.events;

import javafx.stage.Stage;

public class LCCreatedEvent extends BaseUIEvent {
    private final String name;

    public LCCreatedEvent(Stage stage, String clientReference) {
        super(stage);
        this.name = clientReference;
    }

    public String getName() {
        return name;
    }
}
