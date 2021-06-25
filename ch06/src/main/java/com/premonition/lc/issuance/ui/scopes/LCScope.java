package com.premonition.lc.issuance.ui.scopes;

import com.premonition.lc.issuance.domain.LCApplicationId;
import de.saxsys.mvvmfx.Scope;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class LCScope implements Scope {
    private final LCApplicationId lcApplicationId;
    private final String clientReference;



}

