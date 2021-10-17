package com.premonition.lc.ch09.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Data
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor(access = PRIVATE)
public class Decision {
    private boolean approved;
    private String remarks;

    public static Decision accepted() {
        return new Decision(true, "");
    }

    public static Decision rejected(String remarks) {
        return new Decision(false, remarks);
    }

    public boolean isRejected() {
        return !approved;
    }

    @Override
    public String toString() {
        return "valid=" +
                approved +
                (approved ? "" : ", remarks=" + remarks);
    }
}
