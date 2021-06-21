package com.premonition.lc.issuance.ui.scopes;

import com.premonition.lc.issuance.domain.LCApplicationId;
import de.saxsys.mvvmfx.Scope;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class LCScope implements Scope {
    private LCApplicationId lcApplicationId;
    private String clientReference;
}

