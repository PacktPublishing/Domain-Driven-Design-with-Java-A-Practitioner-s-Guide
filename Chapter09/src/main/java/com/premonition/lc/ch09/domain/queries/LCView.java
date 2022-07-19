package com.premonition.lc.ch09.domain.queries;

import com.premonition.lc.ch09.domain.ApplicantId;
import com.premonition.lc.ch09.domain.LCApplicationId;
import com.premonition.lc.ch09.domain.LCState;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class LCView {

    @EmbeddedId
    private LCApplicationId id;
    @Embedded
    private ApplicantId applicantId;
    private String clientReference;
    @Enumerated(EnumType.STRING)
    private LCState state;

    public LCApplicationId getId() {
        return id;
    }

    public String getClientReference() {
        return clientReference;
    }

    public LCState getState() {
        return state;
    }

    public ApplicantId getApplicantId() {
        return applicantId;
    }
}
