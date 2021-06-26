package com.premonition.lc.issuance.ui.views;

import com.premonition.lc.issuance.domain.Currencies;
import com.premonition.lc.issuance.domain.TenorType;
import com.premonition.lc.issuance.ui.utils.UIUtils;
import com.premonition.lc.issuance.ui.viewmodels.LCDetailsViewModel;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.utils.commands.Action;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.SpinnerValueFactory.DoubleSpinnerValueFactory;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.text.NumberFormat;
import java.util.Currency;

@Component
@Log4j2
public class LCDetailsView implements FxmlView<LCDetailsViewModel> {

    @InjectViewModel
    private LCDetailsViewModel viewModel;

    @FXML
    private Spinner<Double> amount;
    @FXML
    private ComboBox<Currency> currency;
    @FXML
    private DatePicker expiryDate;
    @FXML
    private CheckBox revocable;
    @FXML
    private CheckBox transferable;
    @FXML
    private ComboBox<TenorType> tenorType;
    @FXML
    private Spinner<Integer> tenorDays;
    @FXML
    private Label clientReference;

    public void initialize() {
        tenorType.getItems().addAll(TenorType.values());
        currency.getItems().addAll(Currencies.tradeable());
        clientReference.textProperty().bind(viewModel.clientReferenceProperty());
        amount.getEditor().textProperty().bindBidirectional(viewModel.getBasics().amountProperty(),
                NumberFormat.getInstance());
        amount.setValueFactory(new DoubleSpinnerValueFactory(10000, 1000000, 10000, 10000));
        expiryDate.valueProperty().bindBidirectional(viewModel.getBasics().expiryDateProperty());
        revocable.selectedProperty().bindBidirectional(viewModel.getBasics().revocableProperty());
        transferable.selectedProperty().bindBidirectional(viewModel.getBasics().transferableProperty());
        tenorType.valueProperty().bindBidirectional(viewModel.getBasics().tenorTypeProperty());
        tenorDays.getEditor().textProperty().bindBidirectional(viewModel.getBasics().tenorDaysProperty(),
                NumberFormat.getInstance());
        currency.valueProperty().bindBidirectional(viewModel.getBasics().currencyProperty());
        tenorDays.setValueFactory(new IntegerSpinnerValueFactory(10, 100, 10, 1));
    }

    public void exit(ActionEvent event) {
    }

    public void save(ActionEvent event) {
        new Service<Void>() {

            @Override
            protected Task<Void> createTask() {
                return new Action() {
                    @Override
                    protected void action() {
                        viewModel.save();
                    }
                };
            }
        }.start();
    }
}
