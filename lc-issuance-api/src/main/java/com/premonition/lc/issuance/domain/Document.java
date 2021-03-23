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
        CERTIFICATE_OF_ORIGIN("CERTIFICATE OF ORIGIN SIGNED BY THE CHAMBER OF COMMERCE"),
        BILL_OF_LADING("3/3 FULL SET OF MARINE BILLS OF LADING, CONSIGNED TO ORDER OF ISSUING BANK, NOTIFY APPLICANT, MARKED FREIGHT PAID"),
        INSURANCE_CERTIFICATE("INSURANCE CERTIFICATE, COVERING INSTITUTE CARGO CLAUSES, ALL RISKS (INCLUDING STRIKES, RIOTS AND CIVIL COMMOTIONS, IN FAVOUR OF BUYERS"),
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
