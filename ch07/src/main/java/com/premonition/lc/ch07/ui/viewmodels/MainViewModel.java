package com.premonition.lc.ch07.ui.viewmodels;

import com.premonition.lc.ch07.ui.scopes.LoggedInUserScope;
import de.saxsys.mvvmfx.InjectScope;
import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import org.springframework.stereotype.Component;

@Component
public class MainViewModel implements ViewModel {

    @InjectScope
    private LoggedInUserScope userScope;

    public LoggedInUserScope getUserScope() {
        return userScope;
    }

    public StringProperty stageTitleProperty() {
        return new SimpleStringProperty("LC Issuance");
    }
}
