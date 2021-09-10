package com.premonition.lc.ch08.ui.viewmodels;

import com.premonition.lc.ch08.ui.models.LCDetailsModel;
import com.premonition.lc.ch08.ui.scopes.LCScope;
import com.premonition.lc.ch08.ui.services.BackendService;
import de.saxsys.mvvmfx.InjectScope;
import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.springframework.stereotype.Component;

@Component
public class LCDetailsViewModel implements ViewModel {
    private final StringProperty clientReference;

    private final BackendService service;
    private final LCBasicsViewModel basics;

    @InjectScope
    private LCScope lcScope;

    public LCDetailsViewModel(BackendService service) {
        this.service = service;
        this.clientReference = new SimpleStringProperty(this, "name", "");
        basics = new LCBasicsViewModel();
    }

    public void initialize() {
        this.setClientReference(lcScope.getClientReference());
    }

    public String getClientReference() {
        return clientReference.get();
    }

    public void setClientReference(String clientReference) {
        this.clientReference.set(clientReference);
    }

    public StringProperty clientReferenceProperty() {
        return clientReference;
    }

    public LCBasicsViewModel getBasics() {
        return basics;
    }
    public StringProperty stageTitleProperty() {
        return new SimpleStringProperty("LC Issuance");
    }

    public void save() {
        service.saveLC(this.getModel());
    }

    public void exit() {

    }
    private LCDetailsModel getModel() {
        return new LCDetailsModel(lcScope.getLcApplicationId(), getClientReference());
    }
}
