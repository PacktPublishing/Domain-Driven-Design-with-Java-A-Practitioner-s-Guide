package com.premonition.lc.ch06.ui.viewmodels;

import de.saxsys.mvvmfx.Scope;
import lombok.Data;

@Data
public class LoggedInUserScope implements Scope {
    private final String loggedInUserId;

    public String getLoggedInUserId() {
        return loggedInUserId;
    }
}
