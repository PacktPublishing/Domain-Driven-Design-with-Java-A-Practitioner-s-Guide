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

@Component
@Log4j2
public class CreateLCView implements FxmlView<CreateLCViewModel> {

    @FXML
    private TextField clientReference;
    @FXML
    private Button createButton;

    @InjectViewModel
    private CreateLCViewModel viewModel;

    public CreateLCView() {
    }

    public void create() {
        viewModel.getCreateLCCommand().execute();
    }

    public void initialize() {
        createButton.disableProperty().bind(viewModel.createDisabledProperty());
        clientReference.textProperty().bindBidirectional(viewModel.clientReferenceProperty());
    }

}
