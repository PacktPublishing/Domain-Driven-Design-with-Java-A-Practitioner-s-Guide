package com.premonition.lc.issuance.ui.viewmodels;

import com.premonition.lc.issuance.ui.scopes.LCScope;
import de.saxsys.mvvmfx.InjectScope;
import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.springframework.stereotype.Component;

@Component
public class LCDetailsViewModel implements ViewModel {
    private final StringProperty clientReference;

    private final LCBasicsViewModel basics;

    @InjectScope
    private LCScope lcScope;

    public LCDetailsViewModel() {
        this.clientReference = new SimpleStringProperty(this, "name", "");
        basics = new LCBasicsViewModel();
    }

    public void initialize() {
        this.setClientReference(lcScope.getClientReference());
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
