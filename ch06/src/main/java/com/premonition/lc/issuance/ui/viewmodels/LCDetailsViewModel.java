package com.premonition.lc.issuance.ui.viewmodels;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LCDetailsViewModel implements ViewModel {
    private final StringProperty clientReference;

    private final LCBasicsViewModel basics;

    public LCDetailsViewModel(String clientReference) {
        this.clientReference = new SimpleStringProperty(this, "name", clientReference);
        basics = new LCBasicsViewModel();
    }

    public LCDetailsViewModel() {
        this("");
    }

    public String getClientReference() {
        return clientReference.get();
    }

    public StringProperty clientReferenceProperty() {
        return clientReference;
    }

    public void setClientReference(String clientReference) {
        this.clientReference.set(clientReference);
    }

    public LCBasicsViewModel getBasics() {
        return basics;
    }
}
