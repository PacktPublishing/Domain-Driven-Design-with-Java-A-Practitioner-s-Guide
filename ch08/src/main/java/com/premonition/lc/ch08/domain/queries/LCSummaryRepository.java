package com.premonition.lc.ch08.domain.queries;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LCSummaryRepository extends JpaRepository<LCSummaryView, UUID> {
}
