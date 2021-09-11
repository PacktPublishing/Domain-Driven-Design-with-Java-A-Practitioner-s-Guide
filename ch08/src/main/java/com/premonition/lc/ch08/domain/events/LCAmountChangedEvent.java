package com.premonition.lc.ch08.domain.events;

import com.premonition.lc.ch08.domain.LCApplicationId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.eventhandling.EventMessage;
import org.javamoney.moneta.Money;

import javax.money.MonetaryAmount;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LCAmountChangedEvent {
    private LCApplicationId id;
    private MonetaryAmount amount;

}
