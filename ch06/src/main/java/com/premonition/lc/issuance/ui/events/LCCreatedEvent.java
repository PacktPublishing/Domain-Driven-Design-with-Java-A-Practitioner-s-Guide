package com.premonition.lc.issuance.ui.events;

import javafx.stage.Stage;

public class LCCreatedEvent extends BaseUIEvent {
    private final String name;

    public LCCreatedEvent(Stage stage, String name) {
        super(stage);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
