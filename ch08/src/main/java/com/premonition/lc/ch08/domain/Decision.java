package com.premonition.lc.ch08.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Data
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor(access = PRIVATE)
public class Decision {
    private boolean valid;
    private String remarks;

    public static Decision accepted() {
        return new Decision(true, "");
    }

    public static Decision rejected(String remarks) {
        return new Decision(false, remarks);
    }

    @Override
    public String toString() {
        return "valid=" +
                valid +
                (valid ? "" : ", remarks=" + remarks);
    }
}
