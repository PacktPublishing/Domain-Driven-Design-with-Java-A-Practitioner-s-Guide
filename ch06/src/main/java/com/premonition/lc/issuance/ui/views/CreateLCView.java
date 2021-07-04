package com.premonition.lc.issuance.ui.views;

import com.premonition.lc.issuance.ui.scopes.LCScope;
import com.premonition.lc.issuance.ui.utils.UIUtils;
import com.premonition.lc.issuance.ui.viewmodels.CreateLCViewModel;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class CreateLCView implements FxmlView<CreateLCViewModel> {

    @FXML
    private TextField clientReference;
    @FXML
    private Button createButton;

    @InjectViewModel
    private CreateLCViewModel viewModel;

    public void create(ActionEvent event) {
        new Service<Void>() {
            @Override
            protected void succeeded() {
                log.info("LC was created successfully!");
                Stage stage = UIUtils.getStage(event);
                showLCDetailsView(stage);
                log.info("LC details view was launched!");
            }

            @Override
            protected Task<Void> createTask() {
                return new Task<>() {
                    @Override
                    protected Void call() {
                        viewModel.createLC();
                        log.info("Backend call was successful!");
                        return null;
                    }
                };
            }

        }.start();
    }

    private void showLCDetailsView(Stage stage) {
        stage.titleProperty().bind(viewModel.lcApplicationIdProperty().asString("LC: %s"));
        LCScope scope = viewModel.getScope();
        final Parent parent = FluentViewLoader.fxmlView(LCDetailsView.class)
                .providedScopes(scope)
                .load().getView();
        stage.setScene(new Scene(parent));
        stage.show();
    }

    public void initialize() {
        createButton.disableProperty().bind(viewModel.createDisabledProperty());
        clientReference.textProperty().bindBidirectional(viewModel.clientReferenceProperty());
    }

}
