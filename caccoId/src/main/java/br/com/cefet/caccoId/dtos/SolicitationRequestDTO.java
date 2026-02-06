package br.com.cefet.caccoId.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Lob;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SolicitationRequestDTO {
    @Schema(description = "Se a carteirinha será apenas virtual", example = "true")
    private boolean virtualOnly;

    @Schema(description = "Local de retirada da carteirinha física", example = "Sala do DCE")
    private String pickupLocation;

    @Schema(description = "Comprovante de matrícula", type = "string", format = "binary")
    private MultipartFile enrollmentProof;

    @Schema(description = "Frente do documento de identidade", type = "string", format = "binary")
    private MultipartFile identityDocumentFront;

    @Schema(description = "Verso do documento de identidade", type = "string", format = "binary")
    private MultipartFile identityDocumentBack;

    @Schema(description = "Foto 3x4 do estudante", type = "string", format = "binary")
    private MultipartFile studentPhoto;

    @Schema(description = "Comprovante de pagamento", type = "string", format = "binary")
    private MultipartFile paymentProof;
}
