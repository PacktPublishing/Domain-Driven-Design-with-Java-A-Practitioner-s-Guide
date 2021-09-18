package com.premonition.lc.ch08.domain.events.inbound;

import com.premonition.lc.ch08.domain.Decision;
import com.premonition.lc.ch08.domain.LCApplicationId;
import com.premonition.lc.ch08.domain.ProductId;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductValueValidatedEvent {
    private LCApplicationId lcApplicationId;
    private ProductId productId;
    private Decision decision;

    public static ProductValueValidatedEvent approved(LCApplicationId lcApplicationId, ProductId productId) {
        return new ProductValueValidatedEvent(lcApplicationId, productId, Decision.accepted());
    }

    public static ProductValueValidatedEvent rejected(LCApplicationId lcApplicationId, ProductId productId, String remarks) {
        return new ProductValueValidatedEvent(lcApplicationId, productId, Decision.rejected(remarks));
    }
}
