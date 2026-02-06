package br.com.cefet.caccoId.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FormRequestDTO {
    @Valid
    @NotNull(message = "Os dados do estudante são obrigatórios")
    @Schema(description = "Informações do estudante")
    private StudentRequestDTO student;

    @Schema(description = "Informações da solicitação da carteirinha")
    private SolicitationRequestDTO solicitation;
}
