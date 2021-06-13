package com.premonition.lc.issuance.ui.views;

import com.premonition.lc.issuance.domain.TenorType;
import com.premonition.lc.issuance.ui.events.LCCreatedEvent;
import com.premonition.lc.issuance.ui.viewmodels.LCDetailsViewModel;
import javafx.css.converter.EnumConverter;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.text.NumberFormat;
import java.util.Comparator;
import java.util.Currency;
import java.util.ResourceBundle;
import java.util.TreeSet;

@Component
@Log4j2
public class LCDetailsView extends BaseView<LCCreatedEvent> {

    public static final Comparator<Currency> CURRENCY_COMPARATOR = (o1, o2) -> String.CASE_INSENSITIVE_ORDER.compare(o1.getCurrencyCode(), o2.getCurrencyCode());
    private final LCDetailsViewModel viewModel;
    public TextField amount;
    public DatePicker expiryDate;
    public CheckBox revocable;
    public CheckBox transferable;
    public ComboBox<TenorType> tenorType;
    public Spinner<Integer> tenorDays;
    public ComboBox<Currency> currency;

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
        final TreeSet<Currency> currencies = new TreeSet<>(CURRENCY_COMPARATOR);
        currencies.addAll(Currency.getAvailableCurrencies());
        currency.getItems().addAll(currencies);
        clientReference.textProperty().bind(viewModel.clientReferenceProperty());
        amount.textProperty().bindBidirectional(viewModel.getBasics().amountProperty(),
                NumberFormat.getInstance());
        expiryDate.valueProperty().bindBidirectional(viewModel.getBasics().expiryDateProperty());
        revocable.selectedProperty().bindBidirectional(viewModel.getBasics().revocableProperty());
        transferable.selectedProperty().bindBidirectional(viewModel.getBasics().transferableProperty());
        tenorType.valueProperty().bindBidirectional(viewModel.getBasics().tenorTypeProperty());
        tenorDays.getEditor().textProperty().bindBidirectional(viewModel.getBasics().tenorDaysProperty(),
                NumberFormat.getInstance());
        currency.valueProperty().bindBidirectional(viewModel.getBasics().currencyProperty());
    }

    @Override
    protected void setupViewModel(LCCreatedEvent event) {
        viewModel.setClientReference(event.getName());
    }
}
