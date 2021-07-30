package com.premonition.lc.ch07.domain.queries;

import com.premonition.lc.ch07.domain.LCApplicationId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LCView {
    @EmbeddedId
    private LCApplicationId id;
    private String clientReference;
}
