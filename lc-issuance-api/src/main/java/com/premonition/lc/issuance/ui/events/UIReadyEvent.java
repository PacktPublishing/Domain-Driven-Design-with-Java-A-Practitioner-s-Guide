package com.premonition.lc.issuance.ui.events;

import javafx.stage.Stage;
import org.springframework.context.ApplicationEvent;

public class UIReadyEvent extends BaseUIEvent {
    public UIReadyEvent(Stage source) {
        super(source);
    }
}
