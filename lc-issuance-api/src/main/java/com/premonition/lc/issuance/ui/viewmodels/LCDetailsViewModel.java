package com.premonition.lc.issuance.ui.viewmodels;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LCDetailsViewModel {
    private StringProperty name;

    public LCDetailsViewModel() {
        name = new SimpleStringProperty(this, "name", "");
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
}
