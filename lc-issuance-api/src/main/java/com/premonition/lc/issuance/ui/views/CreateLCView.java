package com.premonition.lc.issuance.ui.views;

import com.premonition.lc.issuance.ui.events.LCCreatedEvent;
import com.premonition.lc.issuance.ui.events.CreateLCRequestedEvent;
import com.premonition.lc.issuance.ui.viewmodels.SimpleLCViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

    @FXML
    private TextField nameField;
    @FXML
    private Button createButton;

    public CreateLCView(@Value("classpath:/ui/create_lc_view.fxml") Resource ui,
                        ApplicationContext context) {
        super(context, ui);
        this.viewModel = new SimpleLCViewModel();
    }

    public void create(ActionEvent event) {
        log.info("Created a new LC with name: '" + nameField.getText() + "'");
        getContext().publishEvent(new LCCreatedEvent(getStage(), viewModel.getName()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createButton.disableProperty().bind(viewModel.createDisabledProperty());
        nameField.textProperty().bindBidirectional(viewModel.nameProperty());
    }
}
