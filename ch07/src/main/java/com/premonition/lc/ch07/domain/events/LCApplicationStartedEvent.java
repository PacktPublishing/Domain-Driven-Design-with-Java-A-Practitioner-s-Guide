package com.premonition.lc.ch07.domain.events;

import com.premonition.lc.ch07.domain.LCApplicationId;
import com.premonition.lc.ch07.domain.LCState;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LCApplicationStartedEvent {

    private LCApplicationId id;
    private String applicantId;
    private String clientReference;
    private LCState state;

    public LCApplicationStartedEvent(LCApplicationId id, String applicantId, String clientReference, LCState state) {
        this.id = id;
        this.applicantId = applicantId;
        this.clientReference = clientReference;
        this.state = state;
    }

    public LCState getState() {
        return state;
    }

    public void setState(LCState state) {
        this.state = state;
    }
}
