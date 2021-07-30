package com.premonition.lc.ch07.domain.queries;

import com.premonition.lc.ch07.domain.LCApplicationId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LCApplicationViewRepository extends JpaRepository<LCView, LCApplicationId> {
}
