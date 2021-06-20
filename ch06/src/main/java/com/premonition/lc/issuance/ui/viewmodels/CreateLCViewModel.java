package com.premonition.lc.issuance.ui.viewmodels;

import com.premonition.lc.issuance.domain.LCApplicationId;
import com.premonition.lc.issuance.ui.services.CreateLCService;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class CreateLCViewModel implements ViewModel, de.saxsys.mvvmfx.ViewModel {
    private final StringProperty clientReference;
    private final BooleanProperty createDisabled;

    @Value("${application.client.reference.min.length:4}")
    private int minLength =4;

    public CreateLCViewModel() {
        this.clientReference = new SimpleStringProperty(this, "clientReference", "");
        this.createDisabled = new SimpleBooleanProperty(this, "createEnabled");
        this.createDisabled.bind(this.clientReference.length().lessThan(minLength));
    }

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
