package com.premonition.lc.issuance.ui.views;

import com.premonition.lc.issuance.ui.events.LCCreatedEvent;
import com.premonition.lc.issuance.ui.viewmodels.LCDetailsViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@Log4j2
public class LCDetailsView extends BaseView<LCCreatedEvent> {

    private final LCDetailsViewModel viewModel;

    @FXML
    private Label nameLabel;

    public LCDetailsView(@Value("classpath:/ui/lc_details.fxml") Resource ui,
                         ApplicationContext context) {
        super(context, ui);
        this.viewModel = new LCDetailsViewModel();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nameLabel.textProperty().bind(viewModel.nameProperty());
    }

    @Override
    protected void beforeShow(LCCreatedEvent event) {
        viewModel.setName(event.getName());
    }
}
