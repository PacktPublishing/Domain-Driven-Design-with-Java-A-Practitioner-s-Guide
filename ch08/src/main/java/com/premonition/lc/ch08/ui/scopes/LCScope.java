package com.premonition.lc.ch08.ui.scopes;

import com.premonition.lc.ch08.domain.LCApplicationId;
import de.saxsys.mvvmfx.Scope;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class LCScope implements Scope {
    private final LCApplicationId lcApplicationId;
    private final String clientReference;


}

