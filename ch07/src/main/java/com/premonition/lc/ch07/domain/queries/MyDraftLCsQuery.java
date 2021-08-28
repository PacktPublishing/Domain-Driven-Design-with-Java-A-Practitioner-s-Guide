package com.premonition.lc.ch07.domain.queries;

import com.premonition.lc.ch07.domain.LCState;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Pageable;

@Data
public class MyDraftLCsQuery {

    private String applicantId;
    private Pageable page;

    public MyDraftLCsQuery(String applicantId) {
        this.applicantId = applicantId;
        this.page = Pageable.unpaged();
    }
}
