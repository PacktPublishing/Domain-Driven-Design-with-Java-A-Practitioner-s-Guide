package com.premonition.lc.ch08.ui.scopes;

import com.premonition.lc.ch08.domain.ApplicantId;
import de.saxsys.mvvmfx.Scope;
import lombok.Data;

@Data
public class LoggedInUserScope implements Scope {
    private final String loggedInUserId;

    public ApplicantId getLoggedInUserId() {
        return ApplicantId.from(loggedInUserId);
    }
}
