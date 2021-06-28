package com.premonition.lc.issuance.ui.viewmodels;

import com.premonition.lc.issuance.domain.LCApplicationId;
import com.premonition.lc.issuance.ui.scopes.LCScope;
import com.premonition.lc.issuance.ui.services.BackendService;
import de.saxsys.mvvmfx.InjectScope;
import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CreateLCViewModel implements ViewModel {

    private final BackendService service;

    @InjectScope
    private UserScope userScope;

    private final StringProperty clientReference;
    private final BooleanProperty createDisabled;
    private final ObjectProperty<LCApplicationId> lcApplicationId;

    public CreateLCViewModel(@Value("${application.client.reference.min.length:4}") int clientReferenceMinLength,
                             BackendService service) {
        this.service = service;
        this.clientReference = new SimpleStringProperty(this, "clientReference", "");
        this.createDisabled = new SimpleBooleanProperty(this, "createEnabled");
        this.lcApplicationId = new SimpleObjectProperty<>();
        this.createDisabled.bind(this.clientReference.length().lessThan(clientReferenceMinLength));
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

    public LCApplicationId getLcApplicationId() {
        return lcApplicationId.get();
    }

    public ObjectProperty<LCApplicationId> lcApplicationIdProperty() {
        return lcApplicationId;
    }

    public void setLcApplicationId(LCApplicationId lcApplicationId) {
        this.lcApplicationId.set(lcApplicationId);
    }

    public void createLC() {
        lcApplicationId.set(service.createLC(userScope.getLoggedInUserId(), getClientReference()));
    }

    void setUserScope(UserScope userScope) {
        this.userScope = userScope;
    }

    public LCScope getScope() {
        return new LCScope(lcApplicationId.get(), clientReference.get());
    }
}
