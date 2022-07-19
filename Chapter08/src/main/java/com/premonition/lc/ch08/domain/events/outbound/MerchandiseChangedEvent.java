package com.premonition.lc.ch08.domain.events.outbound;

import com.premonition.lc.ch08.domain.LCApplicationId;
import com.premonition.lc.ch08.domain.Merchandise;
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
