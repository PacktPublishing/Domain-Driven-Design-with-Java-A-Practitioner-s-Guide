package com.premonition.lc.issuance.ui.views;

import com.premonition.lc.issuance.ui.events.CreateLCRequestedEvent;
import com.premonition.lc.issuance.ui.events.UIReadyEvent;
import javafx.event.ActionEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class MainView extends BaseView<UIReadyEvent> {

    public MainView(ApplicationContext context,
                    @Value("classpath:/ui/main_menu.fxml") Resource ui) {
        super(context, ui);
    }

    public void launchCreate(ActionEvent actionEvent) {
        getContext().publishEvent(new CreateLCRequestedEvent(getStage()));
    }
}
