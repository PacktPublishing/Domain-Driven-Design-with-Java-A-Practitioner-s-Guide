package com.premonition.lc.issuance.ui.events;

import javafx.stage.Stage;
import org.springframework.context.ApplicationEvent;

public class CreateLCRequestedEvent extends BaseUIEvent {

    public CreateLCRequestedEvent(Stage source) {
        super(source);
    }

}
