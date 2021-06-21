package com.premonition.lc.issuance.ui.scopes;

import com.premonition.lc.issuance.domain.LCApplicationId;
import de.saxsys.mvvmfx.Scope;
import lombok.Data;

@Data
public class LCScope implements Scope {
    private LCApplicationId lcApplicationId;
}

