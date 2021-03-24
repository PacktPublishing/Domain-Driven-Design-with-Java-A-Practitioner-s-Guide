package com.premonition.lc.issuance.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor(access = PRIVATE)
public class DocumentClause {

    public static final DocumentClause CERTIFICATE_OF_ORIGIN = DocumentClause.standard("CERTIFICATE OF ORIGIN SIGNED BY THE CHAMBER OF COMMERCE");
    public static final DocumentClause BILL_OF_LADING = DocumentClause.standard("3/3 FULL SET OF MARINE BILLS OF LADING, CONSIGNED TO ORDER OF ISSUING BANK, NOTIFY APPLICANT, MARKED FREIGHT PAID");
    public static final DocumentClause INSURANCE_CERTIFICATE = DocumentClause.standard("INSURANCE CERTIFICATE, COVERING INSTITUTE CARGO CLAUSES, ALL RISKS (INCLUDING STRIKES, RIOTS AND CIVIL COMMOTIONS, IN FAVOUR OF BUYERS");

    @NotBlank
    private final String description;


    public boolean isCertificateOfOrigin() {
        return this.equals(CERTIFICATE_OF_ORIGIN);
    }

    private static DocumentClause standard(String description) {
        return new DocumentClause(description);
    }

    public static DocumentClause adhoc(String description) {
        return new DocumentClause(description);
    }
}
