package com.premonition.lc.ch08.domain.commands;

import com.premonition.lc.ch08.domain.LCApplicationId;
import com.premonition.lc.ch08.domain.Merchandise;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class ChangeMerchandiseCommand {
    @TargetAggregateIdentifier
    private LCApplicationId id;

    @NotNull
    @Valid
    private Merchandise merchandise;

}
