package com.premonition.lc.issuance.ui.viewmodels;

import de.saxsys.mvvmfx.InjectScope;
import de.saxsys.mvvmfx.ViewModel;
import org.springframework.stereotype.Component;

@Component
public class MainViewModel implements ViewModel {

    @InjectScope
    private UserScope userScope;

    public UserScope getUserScope() {
        return userScope;
    }
}
