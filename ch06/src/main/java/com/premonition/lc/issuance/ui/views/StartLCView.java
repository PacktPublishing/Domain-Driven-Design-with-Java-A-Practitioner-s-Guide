package com.premonition.lc.issuance.ui.views;

import com.premonition.lc.issuance.ui.scopes.LCScope;
import com.premonition.lc.issuance.ui.utils.UIUtils;
import com.premonition.lc.issuance.ui.viewmodels.StartLCViewModel;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.Initialize;
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
import org.springframework.stereotype.Component;

@Component
public class StartLCView implements FxmlView<StartLCViewModel> {

    @FXML
    private TextField clientReference;
    @FXML
    private Button startButton;

    @InjectViewModel
    private StartLCViewModel viewModel;

    public void start(ActionEvent event) {
        new Service<Void>() {
            @Override
            protected void succeeded() {
                Stage stage = UIUtils.getStage(event);
                showLCDetailsView(stage);
            }

            @Override
            protected void failed() {

            }

            @Override
            protected Task<Void> createTask() {
                return new Task<>() {
                    @Override
                    protected Void call() {
                        viewModel.startNewLC();
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

    @Initialize
    public void initialize() {
        startButton.disableProperty().bind(viewModel.startDisabledProperty());
        clientReference.textProperty().bindBidirectional(viewModel.clientReferenceProperty());
    }

}
