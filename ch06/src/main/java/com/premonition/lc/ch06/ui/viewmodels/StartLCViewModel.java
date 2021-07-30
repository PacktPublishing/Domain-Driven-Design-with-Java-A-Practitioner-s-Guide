package com.premonition.lc.ch06.ui.viewmodels;

import com.premonition.lc.ch06.domain.LCApplicationId;
import com.premonition.lc.ch06.ui.scopes.LCScope;
import com.premonition.lc.ch06.ui.services.BackendService;
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
public class StartLCViewModel implements ViewModel {

    private final BackendService service;
    private final StringProperty clientReference;
    private final BooleanProperty startDisabled;
    private final ObjectProperty<LCApplicationId> lcApplicationId;
    @InjectScope
    private LoggedInUserScope userScope;

    public StartLCViewModel(@Value("${application.client.reference.min.length:4}") int clientReferenceMinLength,
                            BackendService service) {
        this.service = service;
        this.clientReference = new SimpleStringProperty();
        this.startDisabled = new SimpleBooleanProperty();
        this.lcApplicationId = new SimpleObjectProperty<>();
        this.startDisabled.bind(createBooleanBinding(exceeds(clientReferenceMinLength),
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

    public boolean getStartDisabled() {
        return startDisabled.get();
    }

    public void setStartDisabled(boolean startDisabled) {
        this.startDisabled.set(startDisabled);
    }

    public BooleanProperty startDisabledProperty() {
        return startDisabled;
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

    public void startNewLC() {
        if (!getStartDisabled()) {
            lcApplicationId.set(service.startNewLC(userScope.getLoggedInUserId(), getClientReference()));
        }
    }

    void setLoggedInUser(LoggedInUserScope userScope) {
        this.userScope = userScope;
    }

    public LCScope getScope() {
        return new LCScope(lcApplicationId.get(), clientReference.get());
    }
}
