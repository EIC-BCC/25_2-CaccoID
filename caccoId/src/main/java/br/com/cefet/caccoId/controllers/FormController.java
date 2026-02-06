package br.com.cefet.caccoId.controllers;


import br.com.cefet.caccoId.dtos.ApiResponseDTO;
import br.com.cefet.caccoId.dtos.FormRequestDTO;
import br.com.cefet.caccoId.services.FormService;
import br.com.cefet.caccoId.services.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
@RequestMapping("/form")
@Tag(name = "Gerenciamento do Formulário", description = "Endpoints para gerenciar o formulário")
public class FormController {
    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private FormService formService;
    
    @Autowired
    private EmailService emailService;

    @Operation(
            summary = "Registra um novo estudante",
            description = "Recebe os dados do formulário de solicitação (com possíveis arquivos) e registra um novo estudante. Requer autenticação."
    )
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Estudante registrado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Erro de validação ou dados inválidos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Usuário não autenticado ou sem permissão",
                    content = @Content(mediaType = "application/json")
            )
    })
    @PostMapping("/register-student")
    public  ResponseEntity<ApiResponseDTO<Object>> registerStudent(
            @Valid @ModelAttribute FormRequestDTO formRequestDTO){
        try {
            formService.registerStudent(formRequestDTO);
            var response = new ApiResponseDTO<>(true, "Estudante registrado com sucesso!", null);
            log.info("Enviando email para " + formRequestDTO.getStudent().getEmail());
            String email = formRequestDTO.getStudent().getEmail();
            emailService.sendEmail(email, "Solicitação de carteirinha no CaccoID","Sua solicitação foi realizada com sucesso! Aguarde as atualizações do status pelo e-mail ou entre no site para conferir.");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException | DataIntegrityViolationException e) {
            var response = new ApiResponseDTO<>(false, e.getMessage(),null);
            return ResponseEntity.badRequest().body(response);
        }
    }
}
