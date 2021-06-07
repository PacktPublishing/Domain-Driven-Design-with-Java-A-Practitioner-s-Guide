package com.premonition.lc.issuance.ui.viewmodels;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SimpleLCViewModel {
    private final StringProperty name;
    private final BooleanProperty createDisabled;

    public SimpleLCViewModel() {
        this("");
    }

    public SimpleLCViewModel(String name) {
        this.name = new SimpleStringProperty(this, "name", name);
        this.createDisabled = new SimpleBooleanProperty(this, "createEnabled");
        this.createDisabled.bind(this.name.length().lessThan(4));
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public boolean getCreateDisabled() {
        return createDisabled.get();
    }

    public BooleanProperty createDisabledProperty() {
        return createDisabled;
    }

    public void setCreateDisabled(boolean createDisabled) {
        this.createDisabled.set(createDisabled);
    }

    public void save() {
        setName("");
    }

    public void initWithName(String name) {
        setName(name);
    }
}
