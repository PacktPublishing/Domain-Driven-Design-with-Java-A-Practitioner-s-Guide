package com.premonition.lc.issuance.ui.viewmodels;

import com.premonition.lc.issuance.domain.LCApplicationId;
import com.premonition.lc.issuance.ui.scopes.LCScope;
import com.premonition.lc.issuance.ui.services.CreateLCService;
import de.saxsys.mvvmfx.InjectScope;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.Command;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CreateLCViewModel implements de.saxsys.mvvmfx.ViewModel {

    private final CreateLCService service;

    @InjectScope
    private UserScope userScope;

    private final LCScope lcScope;

    private StringProperty clientReference;
    private BooleanProperty createDisabled;

    public CreateLCViewModel(@Value("${application.client.reference.min.length:4}") int clientReferenceMinLength,
                             CreateLCService service) {
        this.service = service;
        this.lcScope = new LCScope();
        this.clientReference = new SimpleStringProperty(this, "clientReference", "");
        this.createDisabled = new SimpleBooleanProperty(this, "createEnabled");
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

    public void createLC() {
        if (!getCreateDisabled()) {
            new Service<LCApplicationId>() {
                @Override
                protected void succeeded() {
                    lcScope.setLcApplicationId(getValue());
                    lcScope.setClientReference(clientReference.get());
                }

                @Override
                protected Task<LCApplicationId> createTask() {
                    return new Task<>() {
                        @Override
                        protected LCApplicationId call()  {
                            return service.createLC(userScope.getLoggedInUserId(), CreateLCViewModel.this.getClientReference());
                        }
                    };
                }

            }.start();
        }
    }

    public LCScope getLCScope() {
        return lcScope;
    }

    void setUserScope(UserScope userScope) {
        this.userScope = userScope;
    }
}
