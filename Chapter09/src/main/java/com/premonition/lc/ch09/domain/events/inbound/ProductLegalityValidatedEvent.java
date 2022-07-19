package com.premonition.lc.ch09.domain.events.inbound;

import com.premonition.lc.ch09.domain.Decision;
import com.premonition.lc.ch09.domain.LCApplicationId;
import com.premonition.lc.ch09.domain.ProductId;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductLegalityValidatedEvent {
    private LCApplicationId lcApplicationId;
    private ProductId productId;
    private Decision decision;

    public static ProductLegalityValidatedEvent approved(LCApplicationId lcApplicationId, ProductId productId) {
        return new ProductLegalityValidatedEvent(lcApplicationId, productId, Decision.accepted());
    }

    public static ProductLegalityValidatedEvent rejected(LCApplicationId lcApplicationId, ProductId productId, String remarks) {
        return new ProductLegalityValidatedEvent(lcApplicationId, productId, Decision.rejected(remarks));
    }
}
