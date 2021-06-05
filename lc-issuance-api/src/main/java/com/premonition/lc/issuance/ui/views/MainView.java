package com.premonition.lc.issuance.ui.views;

import com.premonition.lc.issuance.ui.events.LaunchCreateLCViewEvent;
import com.premonition.lc.issuance.ui.events.LaunchMainViewEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class MainView implements ApplicationListener<LaunchMainViewEvent>, Initializable {

    private final ApplicationContext context;
    private final Resource mainView;
    private Stage stage;

    public MainView(ApplicationContext context,
                    @Value("classpath:/main_menu.fxml") Resource mainView) {
        this.context = context;
        this.mainView = mainView;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    @SneakyThrows
    public void onApplicationEvent(LaunchMainViewEvent event) {
        stage = event.getStage();
        showView(mainView);
    }

    private void showView(Resource view) throws IOException {
        FXMLLoader loader = new FXMLLoader(view.getURL());
        loader.setControllerFactory(context::getBean);
        final Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void launchCreate(ActionEvent actionEvent) {
        context.publishEvent(new LaunchCreateLCViewEvent(stage));
    }
}
