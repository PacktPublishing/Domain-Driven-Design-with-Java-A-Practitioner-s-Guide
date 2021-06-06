package com.premonition.lc.issuance.ui.events;

import javafx.stage.Stage;
import org.springframework.context.ApplicationEvent;

public class LaunchMainViewEvent extends BaseUIEvent {
    public LaunchMainViewEvent(Stage source) {
        super(source);
    }
}
