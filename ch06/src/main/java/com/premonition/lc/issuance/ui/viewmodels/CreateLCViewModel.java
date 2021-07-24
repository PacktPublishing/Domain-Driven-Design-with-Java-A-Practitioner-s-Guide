package com.premonition.lc.issuance.ui.viewmodels;

import com.premonition.lc.issuance.domain.LCApplicationId;
import com.premonition.lc.issuance.ui.scopes.LCScope;
import com.premonition.lc.issuance.ui.services.BackendService;
import de.saxsys.mvvmfx.InjectScope;
import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;

import static javafx.beans.binding.Bindings.createBooleanBinding;

@Component
@Log4j2
public class CreateLCViewModel implements ViewModel {

    private final BackendService service;
    private final StringProperty clientReference;
    private final BooleanProperty createDisabled;
    private final ObjectProperty<LCApplicationId> lcApplicationId;
    @InjectScope
    private UserScope userScope;

    public CreateLCViewModel(@Value("${application.client.reference.min.length:4}") int clientReferenceMinLength,
                             BackendService service) {
        this.service = service;
        this.clientReference = new SimpleStringProperty(this, "clientReference", "");
        this.createDisabled = new SimpleBooleanProperty(this, "createEnabled");
        this.lcApplicationId = new SimpleObjectProperty<>();
        this.createDisabled.bind(createBooleanBinding(exceeds(clientReferenceMinLength),
                clientReference));
    }

    private Callable<Boolean> exceeds(int clientReferenceMinLength) {
        return () -> {
            final String value = clientReference.get();
            return value == null || value.trim().length() < clientReferenceMinLength;
        };
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

    public boolean getCreateDisabled() {
        return createDisabled.get();
    }

    public void setCreateDisabled(boolean createDisabled) {
        this.createDisabled.set(createDisabled);
    }

    public BooleanProperty createDisabledProperty() {
        return createDisabled;
    }

    public LCApplicationId getLcApplicationId() {
        return lcApplicationId.get();
    }

    public void setLcApplicationId(LCApplicationId lcApplicationId) {
        this.lcApplicationId.set(lcApplicationId);
    }

    public ObjectProperty<LCApplicationId> lcApplicationIdProperty() {
        return lcApplicationId;
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
