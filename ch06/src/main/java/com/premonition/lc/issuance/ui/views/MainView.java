package com.premonition.lc.issuance.ui.views;

import javafx.event.ActionEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class MainView extends BaseView {

    private final CreateLCView createLCView;

    public MainView(ApplicationContext context,
                    @Value("classpath:/ui/main_menu.fxml") Resource ui,
                    CreateLCView createLCView) {
        super(context, ui);
        this.createLCView = createLCView;
    }

    public void launchCreate(ActionEvent event) {
        createLCView.show(getStage());
    }
}
