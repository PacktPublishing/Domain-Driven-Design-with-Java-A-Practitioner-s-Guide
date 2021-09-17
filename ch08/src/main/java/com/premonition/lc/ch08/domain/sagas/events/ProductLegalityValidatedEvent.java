package com.premonition.lc.ch08.domain.sagas.events;

import com.premonition.lc.ch08.domain.LCApplicationId;
import com.premonition.lc.ch08.domain.ProductId;
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
