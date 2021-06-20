package com.premonition.lc.issuance.ui.views;

import com.premonition.lc.issuance.domain.LCApplicationId;
import com.premonition.lc.issuance.ui.viewmodels.CreateLCViewModel;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@Log4j2
public class CreateLCView implements FxmlView<CreateLCViewModel> {

    @FXML
    private TextField clientReference;
    @FXML
    private Button createButton;

    @InjectViewModel
    private CreateLCViewModel viewModel;

//    public CreateLCView(@Value("classpath:/com/premonition/lc/issuance/ui/CreateLCView.fxml") Resource ui,
//                        @Value("${application.client.reference.min.length:4}") int minLength,
//                        ApplicationContext context, CreateLCService service,
//                        LCDetailsView lcDetailsView) {
////        super(context, ui);
//        this.viewModel = new CreateLCViewModel(minLength);
//    }
//
    public void create(ActionEvent event) {
        log.info("Created a new LC with name: '" + clientReference.getText() + "'");
        final Task<LCApplicationId> task = new Task<>() {

            @Override
            protected LCApplicationId call() {
//                return viewModel.createLC(service);
                return null;
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

    public void initialize() {
        createButton.disableProperty().bind(viewModel.createDisabledProperty());
        clientReference.textProperty().bindBidirectional(viewModel.clientReferenceProperty());
    }
}
