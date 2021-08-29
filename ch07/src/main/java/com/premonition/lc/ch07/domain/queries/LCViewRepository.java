package com.premonition.lc.ch07.domain.queries;

import com.premonition.lc.ch07.domain.ApplicantId;
import com.premonition.lc.ch07.domain.LCApplicationId;
import com.premonition.lc.ch07.domain.LCState;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LCViewRepository extends JpaRepository<LCView, LCApplicationId> {
    Page<LCView> findByApplicantIdAndState(ApplicantId applicantId, LCState state, Pageable page);

    @QueryHandler
    default Page<LCView> on(MyDraftLCsQuery query) {
        return findByApplicantIdAndState(
                query.getApplicantId(),
                LCState.DRAFT,
                query.getPage());
    }
}
