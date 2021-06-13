package com.premonition.lc.issuance.ui.viewmodels;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LCDetailsViewModel {
    private final StringProperty clientReference;

    private final LCBasicsViewModel basics;

    public LCDetailsViewModel() {
        clientReference = new SimpleStringProperty(this, "name", "");
        basics = new LCBasicsViewModel();
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
