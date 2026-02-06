package br.com.cefet.caccoId.controllers;

import br.com.cefet.caccoId.dtos.ApiResponseDTO;
import br.com.cefet.caccoId.dtos.ReviewDocumentsDTO;
import br.com.cefet.caccoId.services.ReviewDocumentsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.cefet.caccoId.services.SolicitationService;

@RestController
@RequestMapping("/reviewDocuments")
@Tag(name = "Revisão de campos", description = "Endpoints para revisão de documentos")
public class ReviewDocumentsController {
    @Autowired
    private ReviewDocumentsService reviewDocumentsService;
    @Autowired
    private SolicitationService solicitationService;


    @PostMapping("/markDocumentsForReview/{solicitationID}")
    @Operation(
            summary = "Exigir correção de um dado da solicitação ao aluno",
            description = """
                    Recebe do cliente uma LISTA em JSON com quais documentos foram solicitados para revisão.
                    Exemplo: { "fieldNames": ["email", "phone", "address"] }
                    """,
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido de revisão enviado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Formato de requisição inválido"),
            @ApiResponse(responseCode = "403", description = "Usuário não autenticado ou sem permissão"),
            @ApiResponse(responseCode = "404", description = "Solicitação não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<ApiResponseDTO<?>> markDocumentsForReview(
            @PathVariable Long solicitationID,
            @Valid @RequestBody ReviewDocumentsDTO reviewDocumentsDTO) {
            var solicitation = solicitationService.getSolicitationById(solicitationID);
        try {
            reviewDocumentsService.markForReview(solicitationID, reviewDocumentsDTO.getFieldNames());
            short statusCode = 1; // Status "PENDENTE"
            solicitationService.updateStatus(statusCode, solicitationID); // Corrigido: passa o ID, não o objeto
            return ResponseEntity.ok(new ApiResponseDTO<>(true, "Pedido de revisão enviado com sucesso!", null));

        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponseDTO<>(false, "Solicitação não encontrada.", null));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponseDTO<>(false, e.getMessage(), null));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponseDTO<>(false, "Erro interno do servidor: " + e.getMessage(), null));
        }
    }

}
