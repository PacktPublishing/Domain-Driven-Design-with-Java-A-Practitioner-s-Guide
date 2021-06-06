package com.premonition.lc.issuance.ui.events;

import javafx.stage.Stage;
import org.springframework.context.ApplicationEvent;

public class LaunchCreateLCViewEvent extends BaseUIEvent {

    public LaunchCreateLCViewEvent(Stage source) {
        super(source);
    }

}
