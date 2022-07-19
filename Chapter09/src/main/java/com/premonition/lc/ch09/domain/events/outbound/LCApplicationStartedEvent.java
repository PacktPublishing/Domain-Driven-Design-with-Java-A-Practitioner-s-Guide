package com.premonition.lc.ch09.domain.events.outbound;

import com.premonition.lc.ch09.domain.ApplicantId;
import com.premonition.lc.ch09.domain.LCApplicationId;
import com.premonition.lc.ch09.domain.LCState;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LCApplicationStartedEvent {

    private LCApplicationId id;
    private ApplicantId applicantId;
    private String clientReference;
    private LCState state;

    public LCApplicationStartedEvent(LCApplicationId id, ApplicantId applicantId, String clientReference, LCState state) {
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
