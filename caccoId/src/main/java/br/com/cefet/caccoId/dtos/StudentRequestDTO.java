package br.com.cefet.caccoId.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentRequestDTO {
    @NotBlank(message = "O nome é obrigatório e não pode estar em branco")
    @Schema(description = "Nome completo do estudante", example = "Fubanga de Merda")
    private String name;

    @NotBlank(message = "O e-mail é obrigatório e não pode estar em branco")
    @Email(message = "O e-mail deve ser válido")
    @Schema(description = "E-mail do estudante", example = "fubanga@email.com")
    private String email;

    @NotBlank(message = "O rg é obrigatório não pode estar em branco")
    @Schema(description = "RG do estudante", example = "12.345.678-9")
    private String rg;

    @NotBlank(message = "O cpf é obrigatório e não pode estar em branco")
    @Pattern(
            regexp = "^(\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}|\\d{11})$",
            message = "O cpf deve estar no formato XXX.XXX.XXX-XX ou XXXXXXXXXXX"
    )
    @Schema(description = "CPF do estudante", example = "123.456.789-00")
    private String cpf;

    @NotBlank(message = "O telefone é obrigatório e não pode estar em branco")
    @Schema(description = "Telefone de contato", example = "(21) 91234-5678")
    private String telephone;

    @NotNull(message = "A data de nascimento é obrigatória")
    @Past(message = "A data de nascimento deve estar no passado")
    @Schema(description = "Data de nascimento", example = "2002-05-10", type = "string", format = "date")
    private LocalDate dateOfBirth;

    @Pattern(
            regexp = "^\\d{7}[A-Za-z]{3}$",
            message = "A matrícula deve conter 7 dígitos seguidos de 3 letras.")
    @NotBlank(message = "A matrícula é obrigatória e não pode estar em branco")
    @Schema(description = "Número de matrícula", example = "1234567BCC")
    private String enrollmentNumber;

    @NotBlank(message = "O curso é obrigatório e não pode estar em branco")
    @Schema(description = "Nome do curso", example = "Bacharelado em Ciência da Computação")
    private String program;

    @NotBlank(message = "A instituição é obrigatória e não pode estar em branco")
    @Schema(description = "Instituição de ensino", example = "CEFET-RJ")
    private String institution;

    @NotBlank(message = "O nível de ensino é obrigatório e não pode estar em branco")
    @Schema(description = "Nível de ensino", example = "Graduação")
    private String educationLevel;
}
