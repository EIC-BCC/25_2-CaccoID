package br.com.cefet.caccoId.enums;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;

public enum ReviewableField {

    // -------- Student --------
    CPF("cpf"),
    DATE_OF_BIRTH("date_of_birth"),
    EDUCATION_LEVEL("education_level"),
    EMAIL("email"),
    ENROLLMENT_NUMBER("enrollment_number"),
    INSTITUTION("institution"),
    NAME("name"),
    PROGRAM("program"),
    RG("rg"),
    TELEPHONE("telephone"),

    // -------- Solicitation --------
    ENROLLMENT_PROOF("enrollment_proof"),
    IDENTITY_DOCUMENT_BACK("identity_document_back"),
    IDENTITY_DOCUMENT_FRONT("identity_document_front"),
    PAYMENT_PROOF("payment_proof"),
    STUDENT_PHOTO("student_photo");

    private final String fieldName;

    ReviewableField(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    /** Verifica se um nome está na lista de campos revisáveis */
    public static boolean isValid(String name) {
        return Arrays.stream(values())
                .anyMatch(f -> f.fieldName.equalsIgnoreCase(name));
    }

    /** Retorna todos os nomes válidos como Set<String> */
    public static Set<String> allFieldNames() {
        Set<String> set = new HashSet<>();
        for (ReviewableField f : values()) {
            set.add(f.fieldName);
        }
        return set;
    }
}
