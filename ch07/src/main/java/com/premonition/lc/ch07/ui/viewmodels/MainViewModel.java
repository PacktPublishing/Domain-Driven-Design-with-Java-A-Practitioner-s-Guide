package com.premonition.lc.ch07.ui.viewmodels;

import com.premonition.lc.ch07.domain.queries.LCView;
import com.premonition.lc.ch07.ui.scopes.LoggedInUserScope;
import com.premonition.lc.ch07.ui.services.BackendService;
import de.saxsys.mvvmfx.InjectScope;
import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MainViewModel implements ViewModel {

    @InjectScope
    private LoggedInUserScope userScope;

    private final BackendService service;

    public MainViewModel(BackendService service) {
        this.service = service;
    }

    public LoggedInUserScope getUserScope() {
        return userScope;
    }

    public String loggedInUserId() {
        return userScope.getLoggedInUserId();
    }

    public StringProperty stageTitleProperty() {
        return new SimpleStringProperty("LC Issuance");
    }

    public List<LCView> findMyDraftLCs() {
        return service.findMyDraftLCs(userScope.getLoggedInUserId());
    }
}
