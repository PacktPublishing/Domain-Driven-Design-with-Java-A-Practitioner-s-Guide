package com.premonition.lc.issuance.ui.views;

import com.premonition.lc.issuance.ui.utils.UIUtils;
import com.premonition.lc.issuance.ui.viewmodels.CreateLCViewModel;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
        viewModel.createLC();
        Stage stage = UIUtils.getStage(event);
        showLCDetailsView(stage);
    }

    private void showLCDetailsView(Stage stage) {
        stage.setTitle("LC : " + viewModel.getLCScope().getLcApplicationId());
        final Parent parent = FluentViewLoader.fxmlView(LCDetailsView.class)
                .providedScopes(viewModel.getLCScope())
                .load().getView();
        stage.setScene(new Scene(parent));
        stage.show();
    }

    public void initialize() {
        createButton.disableProperty().bind(viewModel.createDisabledProperty());
        clientReference.textProperty().bindBidirectional(viewModel.clientReferenceProperty());
    }

}
