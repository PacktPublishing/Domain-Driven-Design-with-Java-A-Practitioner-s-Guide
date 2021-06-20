package com.premonition.lc.issuance.ui.viewmodels;

import com.premonition.lc.issuance.domain.LCApplicationId;
import com.premonition.lc.issuance.ui.services.CreateLCService;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CreateLCViewModel implements ViewModel {
    private final StringProperty clientReference;
    private final BooleanProperty createDisabled;

    public CreateLCViewModel(int minClientReferenceLength) {
        this("", minClientReferenceLength);
    }

    public CreateLCViewModel(String clientReference, int minClientReferenceLength) {
        this.clientReference = new SimpleStringProperty(this, "clientReference", clientReference);
        this.createDisabled = new SimpleBooleanProperty(this, "createEnabled");
        this.createDisabled.bind(this.clientReference.length().lessThan(minClientReferenceLength));
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

    public boolean getCreateDisabled() {
        return createDisabled.get();
    }

    public BooleanProperty createDisabledProperty() {
        return createDisabled;
    }

    public void setCreateDisabled(boolean createDisabled) {
        this.createDisabled.set(createDisabled);
    }

    public LCApplicationId createLC(CreateLCService service) {
        if (!getCreateDisabled()) {
            return service.createLC(this.getClientReference());
        }
        throw new IllegalStateException("Trying to save an invalid LC?");
    }
}
