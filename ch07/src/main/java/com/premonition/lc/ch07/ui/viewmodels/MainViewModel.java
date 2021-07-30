package com.premonition.lc.ch07.ui.viewmodels;

import de.saxsys.mvvmfx.InjectScope;
import de.saxsys.mvvmfx.ViewModel;
import org.springframework.stereotype.Component;

@Component
public class MainViewModel implements ViewModel {

    @InjectScope
    private LoggedInUserScope userScope;

    public LoggedInUserScope getUserScope() {
        return userScope;
    }
}
