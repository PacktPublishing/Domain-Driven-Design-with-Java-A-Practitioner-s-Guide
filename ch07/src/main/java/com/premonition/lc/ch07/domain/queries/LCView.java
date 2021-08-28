package com.premonition.lc.ch07.domain.queries;

import com.premonition.lc.ch07.domain.LCApplicationId;
import com.premonition.lc.ch07.domain.LCState;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class LCView {

    @EmbeddedId
    private LCApplicationId id;
    private String applicantId;
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

    public String getApplicantId() {
        return applicantId;
    }
}
