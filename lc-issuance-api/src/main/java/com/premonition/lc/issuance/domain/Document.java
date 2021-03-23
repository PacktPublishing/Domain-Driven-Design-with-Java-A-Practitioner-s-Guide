package com.premonition.lc.issuance.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor(access = PRIVATE)
public class Document {

    public enum Type {
        INSURANCE("Insurance"),
        DRIVING_LICENSE("Driving License"),
        UNKNOWN("");

        private final String description;

        Type(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    @NotNull
    private final Type type;
    @NotBlank
    private final String description;

    private Document(Type type) {
        this.type = type;
        this.description = type.getDescription();
    }

    public static Document standard(Type type) {
        return new Document(type);
    }

    public static Document adhoc(String description) {
        return new Document(Type.UNKNOWN, description);
    }

    public String getDescription() {
        return type != Type.UNKNOWN ? type.getDescription() : description;
    }
}
