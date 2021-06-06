package com.premonition.lc.issuance.ui.views;

import com.premonition.lc.issuance.ui.events.LaunchCreateLCViewEvent;
import com.premonition.lc.issuance.ui.events.LaunchMainViewEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class MainView extends BaseView<LaunchMainViewEvent> {

    public MainView(ApplicationContext context,
                    @Value("classpath:/ui/main_menu.fxml") Resource ui) {
        super(context, ui);
    }

    public void launchCreate(ActionEvent actionEvent) {
        getContext().publishEvent(new LaunchCreateLCViewEvent(getStage()));
    }
}
