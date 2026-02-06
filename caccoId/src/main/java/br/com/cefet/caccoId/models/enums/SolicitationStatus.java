package br.com.cefet.caccoId.models.enums;

import lombok.Getter;

@Getter
public enum SolicitationStatus {
    /*
    IN_PRODUCTION("Em producao"),
    SENT("Enviada"),
    DELIVERED("Entregue"),
  */

    UNDER_REVIEW((short)0, "EM_ANALISE"),
    PENDING((short)1, "PENDENTE"),
    AUTHORIZED((short)2, "AUTORIZADA"),
    ISSUED((short)3, "EMITIDA"),
    EXCLUDED((short)4, "EXCLUIDA");

    private final short code;
    private final String status;

    SolicitationStatus(short code, String status) {
        this.code = code;
        this.status = status;
    }

    //TODO Alterar lógica do sequenciamento de estados
//    public SolicitationStatus next() {
//        return switch (this) {
//            case UNDER_REVIEW -> PENDING;
//            case PENDING, AUTHORIZED -> AUTHORIZED;
//        };
//    }

    public static SolicitationStatus fromCode(short code) {
        return switch(code) {
            case 0 -> SolicitationStatus.UNDER_REVIEW;
            case 1 -> SolicitationStatus.PENDING;
            case 2 -> SolicitationStatus.AUTHORIZED;
            case 3 -> SolicitationStatus.ISSUED;
            case 4 -> SolicitationStatus.EXCLUDED;
            default -> throw new IllegalArgumentException("Código inválido: " + code);
        };
    }


    public static SolicitationStatus fromString(String status) {
        for (SolicitationStatus s : values()) {
            if (s.getStatus().equalsIgnoreCase(status)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Status inválido: " + status);
    }
}
