package com.premonition.lc.ch09.ui.viewmodels;

import com.premonition.lc.ch09.domain.TenorType;
import javafx.beans.property.*;

import java.time.LocalDate;
import java.util.Currency;

public class LCBasicsViewModel {
    private final DoubleProperty amount;
    private final SimpleObjectProperty<Currency> currency;
    private final ObjectProperty<LocalDate> expiryDate;
    private final BooleanProperty transferable;
    private final BooleanProperty revocable;
    private final ObjectProperty<TenorType> tenorType;
    private final IntegerProperty tenorDays;

    public LCBasicsViewModel() {
        amount = new SimpleDoubleProperty();
        currency = new SimpleObjectProperty<>(Currency.getInstance("USD"));
        expiryDate = new SimpleObjectProperty<>();
        transferable = new SimpleBooleanProperty();
        revocable = new SimpleBooleanProperty();
        tenorType = new SimpleObjectProperty<>(TenorType.AT_SIGHT);
        tenorDays = new SimpleIntegerProperty();
    }

    public double getAmount() {
        return amount.get();
    }

    public void setAmount(double amount) {
        this.amount.set(amount);
    }

    public DoubleProperty amountProperty() {
        return amount;
    }

    public LocalDate getExpiryDate() {
        return expiryDate.get();
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate.set(expiryDate);
    }

    public ObjectProperty<LocalDate> expiryDateProperty() {
        return expiryDate;
    }

    public boolean isTransferable() {
        return transferable.get();
    }

    public void setTransferable(boolean transferable) {
        this.transferable.set(transferable);
    }

    public BooleanProperty transferableProperty() {
        return transferable;
    }

    public boolean isRevocable() {
        return revocable.get();
    }

    public void setRevocable(boolean revocable) {
        this.revocable.set(revocable);
    }

    public BooleanProperty revocableProperty() {
        return revocable;
    }

    public TenorType getTenorType() {
        return tenorType.get();
    }

    public void setTenorType(TenorType tenorType) {
        this.tenorType.set(tenorType);
    }

    public ObjectProperty<TenorType> tenorTypeProperty() {
        return tenorType;
    }

    public int getTenorDays() {
        return tenorDays.get();
    }

    public void setTenorDays(int tenorDays) {
        this.tenorDays.set(tenorDays);
    }

    public IntegerProperty tenorDaysProperty() {
        return tenorDays;
    }

    public Currency getCurrency() {
        return currency.get();
    }

    public void setCurrency(Currency currency) {
        this.currency.set(currency);
    }

    public SimpleObjectProperty<Currency> currencyProperty() {
        return currency;
    }
}
