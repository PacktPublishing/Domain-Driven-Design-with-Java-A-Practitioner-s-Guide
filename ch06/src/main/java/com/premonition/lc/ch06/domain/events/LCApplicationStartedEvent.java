package com.premonition.lc.ch06.domain.events;

import com.premonition.lc.ch06.domain.AdvisingBank;
import com.premonition.lc.ch06.domain.LCApplicationId;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LCApplicationStartedEvent {

    private LCApplicationId id;
    private String applicantId;
    private String clientReference;
    private AdvisingBank advisingBank;

    public LCApplicationStartedEvent(LCApplicationId id, String applicantId, String clientReference) {
        this.id = id;
        this.applicantId = applicantId;
        this.clientReference = clientReference;
    }
}
