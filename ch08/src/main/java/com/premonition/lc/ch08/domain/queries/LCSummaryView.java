package com.premonition.lc.ch08.domain.queries;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
public class LCSummaryView {
    @Id
    private UUID id;
    private int count;

    public LCSummaryView() {
        this.id = UUID.randomUUID();
        this.count = 0;
    }

    public LCSummaryView increment() {
        count++;
        return this;
    }
}
