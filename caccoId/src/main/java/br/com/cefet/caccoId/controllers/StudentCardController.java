package br.com.cefet.caccoId.controllers;

import br.com.cefet.caccoId.dtos.ApiResponseDTO;
import br.com.cefet.caccoId.services.StudentCardService;
import br.com.cefet.caccoId.dtos.StudentCardDTO;

import br.com.cefet.caccoId.services.SolicitationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.persistence.EntityNotFoundException;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/student-card")
@Tag(name = "Endpoint de carteirinhas", description = "Endpoints para gerenciar requisições de carteirinhas estudantis")
public class StudentCardController {
    @Autowired
    private StudentCardService studentCardService;
    @Autowired
    private SolicitationService solicitationService;

    @GetMapping("/get-card-by-hash/{student_card_hash}")
    @Operation(
            summary = "Obter carteirinha estudantil por hash",
            description = "Obtém a carteirinha estudantil de um aluno específico.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Dados da carteirinha retornados com sucesso"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Carteirinha não encontrada"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Usuário não autenticado ou sem permissão"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno ao retornar dados da solicitação"
            )
    })

    public ResponseEntity<ApiResponseDTO<?>> getStudentCardByToken(
            @Parameter(description = "Hash da carteirinha estudantil", required = true)
            @PathVariable String student_card_hash) {
        try {
            var studentCardData = studentCardService.getStudentCardByToken(student_card_hash);
            return ResponseEntity.ok(new ApiResponseDTO<>(true, "Carteirinha obtida com sucesso", studentCardData));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponseDTO<>(false, "Carteirinha não encontrada.", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponseDTO<>(false, "Erro interno do servidor: " + e.getMessage(), null));
        }
    }

    @PostMapping("/create/{studentId}")
    @Operation(
            summary = "Recebe o id do aluno e cria a carteirinha estudantil",
            description = "Cria a carteirinha estudantil de um aluno.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Carteirinha emitida com sucesso!"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Estudante não encontrado"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Usuário não autenticado ou sem permissão"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno ao retornar dados da solicitação"
            )
    })
    public ResponseEntity<ApiResponseDTO<StudentCardDTO>> createCard(@PathVariable Long studentId) {
        try {
            StudentCardDTO createdCard = studentCardService.createStudentCard(studentId);

            return ResponseEntity.ok(new ApiResponseDTO<StudentCardDTO>(true, "Carteirinha obtida com sucesso", createdCard));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponseDTO<>(false, "Estudante não encontrado.", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponseDTO<>(false, "Erro interno do servidor: " + e.getMessage(), null));
        }
    }

    @GetMapping("/get-card-by-id/{studentId}")
    @Operation(
            summary = "Obter carteirinha estudantil por id",
            description = "Obtém a carteirinha estudantil de um aluno pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Dados da carteirinha retornados com sucesso"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Carteirinha não encontrada"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Usuário não autenticado ou sem permissão"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno ao retornar dados da solicitação"
            )
    })

    public ResponseEntity<ApiResponseDTO<?>> getStudentCardByStudentId(
            @Parameter(description = "Retorna os dados da carteirinha ao receber ID", required = true)
            @PathVariable Long studentId) {
        try {
            var studentCardData = studentCardService.getStudentCardByStudentId(studentId);
            return ResponseEntity.ok(new ApiResponseDTO<>(true, "Carteirinha obtida com sucesso", studentCardData));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponseDTO<>(false, "Carteirinha não encontrada.", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponseDTO<>(false, "Erro interno do servidor: " + e.getMessage(), null));
        }
    }
}