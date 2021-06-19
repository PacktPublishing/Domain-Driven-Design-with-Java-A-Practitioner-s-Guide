package com.premonition.lc.issuance.ui.views;

import com.premonition.lc.issuance.domain.LCApplicationId;
import com.premonition.lc.issuance.ui.events.CreateLCRequestedEvent;
import com.premonition.lc.issuance.ui.events.LCCreatedEvent;
import com.premonition.lc.issuance.ui.services.CreateLCService;
import com.premonition.lc.issuance.ui.viewmodels.CreateLCViewModel;
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

    private final CreateLCViewModel viewModel;
    private final CreateLCService service;

    @FXML
    private TextField clientReference;
    @FXML
    private Button createButton;
    private LCDetailsView lcDetailsView;

    public CreateLCView(@Value("classpath:/ui/create_lc_view.fxml") Resource ui,
                        @Value("${application.client.reference.min.length:4}") int minLength,
                        ApplicationContext context, CreateLCService service,
                        LCDetailsView lcDetailsView) {
        super(context, ui);
        this.service = service;
        this.viewModel = new CreateLCViewModel(minLength);
        this.lcDetailsView = lcDetailsView;
    }

    public void create(ActionEvent event) {
        log.info("Created a new LC with name: '" + clientReference.getText() + "'");
        final Task<LCApplicationId> task = new Task<>() {

            @Override
            protected LCApplicationId call() {
                return viewModel.createLC(service);
            }

            @Override
            protected void succeeded() {
                log.info("Displaying a message!");
                final LCApplicationId id = getValue();
                new Alert(Alert.AlertType.INFORMATION, "Successfully created a new LC with id: " + id)
                        .showAndWait();
                lcDetailsView.showView(new LCCreatedEvent(getStage(), viewModel.getClientReference()));
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
        clientReference.textProperty().bindBidirectional(viewModel.clientReferenceProperty());
    }
}
