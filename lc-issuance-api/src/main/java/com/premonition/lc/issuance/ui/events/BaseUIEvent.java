package com.premonition.lc.issuance.ui.events;

import javafx.stage.Stage;
import org.springframework.context.ApplicationEvent;

public class BaseUIEvent extends ApplicationEvent {
    protected BaseUIEvent(Stage source) {
        super(source);
    }

    public Stage getStage() {
        return (Stage) getSource();
    }
}
