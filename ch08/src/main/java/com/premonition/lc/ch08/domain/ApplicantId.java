package com.premonition.lc.ch08.domain;

import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
@NoArgsConstructor
public class ApplicantId {

    private UUID applicantId;

    public ApplicantId(UUID id) {
        this.applicantId = id;
    }

    public static ApplicantId randomId() {
        return new ApplicantId(UUID.randomUUID());
    }

    public static ApplicantId from(String input) {
        return new ApplicantId(UUID.nameUUIDFromBytes(input.getBytes()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApplicantId that = (ApplicantId) o;
        return applicantId.equals(that.applicantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(applicantId);
    }
}
