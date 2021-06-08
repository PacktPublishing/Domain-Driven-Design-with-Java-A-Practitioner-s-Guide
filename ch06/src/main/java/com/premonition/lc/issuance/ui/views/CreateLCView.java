package com.premonition.lc.issuance.ui.views;

import com.premonition.lc.issuance.domain.LCApplicationId;
import com.premonition.lc.issuance.ui.events.CreateLCRequestedEvent;
import com.premonition.lc.issuance.ui.services.CreateLCService;
import com.premonition.lc.issuance.ui.viewmodels.SimpleLCViewModel;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@Log4j2
public class CreateLCView extends BaseView<CreateLCRequestedEvent> {

    private final SimpleLCViewModel viewModel;
    private final CreateLCService service;

    @FXML
    private TextField nameField;
    @FXML
    private Button createButton;

    public CreateLCView(@Value("classpath:/ui/create_lc_view.fxml") Resource ui,
                        ApplicationContext context, CreateLCService service) {
        super(context, ui);
        this.service = service;
        this.viewModel = new SimpleLCViewModel();
    }

    public void create(ActionEvent event) {
        log.info("Created a new LC with name: '" + nameField.getText() + "'");
        final Task<LCApplicationId> task = new Task<>() {

            @Override
            protected LCApplicationId call() {
                return service.createLC(viewModel.getName());
            }

            @Override
            protected void succeeded() {
                log.info("Displaying a message!");
                final LCApplicationId id = getValue();
                new Alert(Alert.AlertType.INFORMATION, "Successfully created a new LC with id: " + id)
                        .showAndWait();
            }

            @Override
            protected void failed() {
                new Alert(Alert.AlertType.ERROR, "Failed to create a new LC!")
                        .showAndWait();
            }
        };
        new Thread(task).start();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createButton.disableProperty().bind(viewModel.createDisabledProperty());
        nameField.textProperty().bindBidirectional(viewModel.nameProperty());
    }
}
