package com.premonition.lc.ch08.domain.queries;

import com.premonition.lc.ch08.domain.ApplicantId;
import lombok.Data;
import org.springframework.data.domain.Pageable;

@Data
public class MyDraftLCsQuery {

    private ApplicantId applicantId;
    private Pageable page;

    public MyDraftLCsQuery(ApplicantId applicantId) {
        this.applicantId = applicantId;
        this.page = Pageable.unpaged();
    }
}
