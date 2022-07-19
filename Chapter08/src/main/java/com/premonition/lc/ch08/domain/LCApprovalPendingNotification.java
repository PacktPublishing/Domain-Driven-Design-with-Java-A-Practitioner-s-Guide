package com.premonition.lc.ch08.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor(access = PRIVATE)
public class LCApprovalPendingNotification {
    private final LCApplicationId id;
    private final int reminderNumber;

    public static LCApprovalPendingNotification first(LCApplicationId id) {
        return new LCApprovalPendingNotification(id, 1);
    }

    public LCApprovalPendingNotification next() {
        return new LCApprovalPendingNotification(id, reminderNumber + 1);
    }
}
