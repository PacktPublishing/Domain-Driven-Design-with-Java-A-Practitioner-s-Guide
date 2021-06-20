package com.premonition.lc.issuance.ui.views;

import com.premonition.lc.issuance.domain.Currencies;
import com.premonition.lc.issuance.domain.TenorType;
import com.premonition.lc.issuance.ui.viewmodels.LCDetailsViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.SpinnerValueFactory.DoubleSpinnerValueFactory;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.text.NumberFormat;
import java.util.*;

@Component
@Log4j2
public class LCDetailsView extends BaseView<LCDetailsViewModel> {

    private final LCDetailsViewModel viewModel;
    public Spinner<Double> amount;
    public ComboBox<Currency> currency;
    public DatePicker expiryDate;
    public CheckBox revocable;
    public CheckBox transferable;
    public ComboBox<TenorType> tenorType;
    public Spinner<Integer> tenorDays;

    @FXML
    private Label clientReference;

    public LCDetailsView(@Value("classpath:/ui/lc_details.fxml") Resource ui,
                         ApplicationContext context) {
        super(context, ui);
        this.viewModel = new LCDetailsViewModel();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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

}
