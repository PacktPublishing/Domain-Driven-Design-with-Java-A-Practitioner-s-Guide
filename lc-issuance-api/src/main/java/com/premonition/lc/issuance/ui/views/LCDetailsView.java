package com.premonition.lc.issuance.ui.views;

import com.premonition.lc.issuance.ui.events.LaunchLCDetailsViewEvent;
import com.premonition.lc.issuance.ui.viewmodels.LCDetailsViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
public class LCDetailsView implements ApplicationListener<LaunchLCDetailsViewEvent>, Initializable {

    private final Resource ui;
    private final ApplicationContext context;
    private final LCDetailsViewModel viewModel;

    @FXML
    private Label nameLabel;

    public LCDetailsView(@Value("classpath:/lc_details.fxml") Resource ui,
                         ApplicationContext context) {
        this.ui = ui;
        this.context = context;
        this.viewModel = new LCDetailsViewModel();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        viewModel.nameProperty().bindBidirectional(nameLabel.textProperty());
    }

    @Override
    @SneakyThrows
    public void onApplicationEvent(LaunchLCDetailsViewEvent event) {
        Stage stage = event.getStage();
        FXMLLoader loader = new FXMLLoader(ui.getURL());
        loader.setControllerFactory(context::getBean);
        final Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        viewModel.setName(event.getName());
        stage.show();
    }
}
