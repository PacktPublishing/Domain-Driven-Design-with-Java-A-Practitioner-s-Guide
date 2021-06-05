package com.premonition.lc.issuance.ui.events;

import javafx.stage.Stage;
import org.springframework.context.ApplicationEvent;

public class LaunchMainViewEvent extends ApplicationEvent {
    public LaunchMainViewEvent(Object source) {
        super(source);
    }

    public Stage getStage() {
        return (Stage) getSource();
    }
}
