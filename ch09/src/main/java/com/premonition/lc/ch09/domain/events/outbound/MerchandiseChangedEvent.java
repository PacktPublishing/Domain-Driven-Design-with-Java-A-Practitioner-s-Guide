package com.premonition.lc.ch09.domain.events.outbound;

import com.premonition.lc.ch09.domain.LCApplicationId;
import com.premonition.lc.ch09.domain.Merchandise;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MerchandiseChangedEvent {
    private LCApplicationId id;
    private Merchandise merchandise;
}
