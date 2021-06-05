package com.premonition.lc.issuance.ui.views;

import com.premonition.lc.issuance.ui.events.LaunchLCDetailsViewEvent;
import com.premonition.lc.issuance.ui.viewmodels.SimpleLCViewModel;
import com.premonition.lc.issuance.ui.events.LaunchCreateLCViewEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@Log4j2
public class CreateLCView implements ApplicationListener<LaunchCreateLCViewEvent>, Initializable {

    private final Resource ui;
    private final ApplicationContext context;
    private final SimpleLCViewModel viewModel;

    @FXML
    private TextField nameField;
    @FXML
    private Button createButton;
    private Stage stage;

    public CreateLCView(@Value("classpath:/create_lc_view.fxml") Resource ui,
                        ApplicationContext context) {
        this.ui = ui;
        this.context = context;
        this.viewModel = new SimpleLCViewModel();
    }

    public void create(ActionEvent event) {
        log.info("Created a new LC with name: '" + nameField.getText() + "'");
        this.viewModel.save();
        context.publishEvent(new LaunchLCDetailsViewEvent(stage, viewModel.getName()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createButton.disableProperty().bind(viewModel.createDisabledProperty());
        nameField.textProperty().bindBidirectional(viewModel.nameProperty());
    }

    @Override
    @SneakyThrows
    public void onApplicationEvent(LaunchCreateLCViewEvent event) {
        stage = event.getStage();
        FXMLLoader loader = new FXMLLoader(ui.getURL());
        loader.setControllerFactory(context::getBean);
        final Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
