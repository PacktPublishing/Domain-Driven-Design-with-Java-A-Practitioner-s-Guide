package com.premonition.lc.ch07.domain.queries;

import com.premonition.lc.ch07.domain.ApplicantId;
import com.premonition.lc.ch07.domain.LCApplicationId;
import com.premonition.lc.ch07.domain.LCState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;

import java.util.EnumSet;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class LCViewRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private LCViewRepository repository;

    @Test
    void shouldFindAllDraftLCsByApplicantId() {
        createLCs(EnumSet.allOf(LCState.class));

        final ApplicantId applicantId = ApplicantId.randomId();
        entityManager.persistAndFlush(new LCView(
                LCApplicationId.randomId(), applicantId,
                "Testing3", LCState.SUBMITTED));

        final LCView lc1 = entityManager.persistFlushFind(new LCView(
                LCApplicationId.randomId(), applicantId,
                "Testing1", LCState.DRAFT));
        final LCView lc2 = entityManager.persistFlushFind(new LCView(
                LCApplicationId.randomId(), applicantId,
                "Testing2", LCState.DRAFT));

        final Page<LCView> results = repository.on(
                new MyDraftLCsQuery(applicantId));

        assertThat(results.getContent()).containsOnly(lc1, lc2);
    }

    private void createLCs(EnumSet<LCState> states) {
        states.forEach(this::createLC);
    }

    private void createLC(LCState state) {
        entityManager.persistAndFlush(new LCView(
                LCApplicationId.randomId(), ApplicantId.randomId(),
                "Testing3", state));
    }
}