package br.com.cefet.caccoId.controllers;

import br.com.cefet.caccoId.dtos.ApiResponseDTO;
import br.com.cefet.caccoId.dtos.UpdateStatusDTO;
import br.com.cefet.caccoId.models.Solicitation;
import br.com.cefet.caccoId.models.enums.SolicitationStatus;
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
@RequestMapping("/solicitation")
@Tag(name = "Gerenciamento de Solicitações", description = "Endpoints para gerenciar solicitações")
public class SolicitationController {
    @Autowired
    private SolicitationService solicitationService;

    @GetMapping()
    @Operation(
            summary = "Retorna a solicitação do usuário",
            description = "Busca os dados da solicitação ativa do usuário autenticado e retorna formatado, se existir. Requer autenticação."
    )
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Dados da solicitação retornados com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Usuário não possui uma solicitação ativa",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Usuário não autenticado ou sem permissão",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno ao retornar dados da solicitação",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDTO.class))
            )
    })
    public ResponseEntity<ApiResponseDTO<?>> GetStatus(){
        try {
            var solicitation = solicitationService.getSolicitation();
            var body = solicitationService.formatSolicitation(solicitation);
            var response = new ApiResponseDTO<>(true, "Dados da solicitação retornados com sucesso.", body);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch(NullPointerException e){
            var response = new ApiResponseDTO<>(false, "Esse usuário não possui uma solicitação ativa.", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        catch (Exception e){
            var response = new ApiResponseDTO<>(false, "Falha ao retornar dados da solicitação: " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(
            summary = "Atualiza o status da solicitação",
            description = "Atualiza o status da solicitação do usuário autenticado com base nos dados enviados no corpo da requisição. Requer autenticação."
    )
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Status da solicitação atualizado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Requisição malformada ou status inválido",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Usuário não autenticado ou sem permissão",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno ao atualizar o status",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDTO.class))
            )
    })
    @PostMapping("/update")
    public ResponseEntity<ApiResponseDTO<?>> updateSolicitationStatus(@RequestBody UpdateStatusDTO updateStatusDTO){
        try {
            var solicitation = solicitationService.updateStatus(updateStatusDTO.getNewStatus(), null);
            ApiResponseDTO<Map<String, Integer>> response;
            if (!(solicitationService.finalStatusReached(solicitation.getStatus()))) {
                response = new ApiResponseDTO<>(
                        true,
                        "Estado alterado com sucesso.",
                        Map.of("status", solicitation.getStatus().ordinal())
                );
            } else {
                response = new ApiResponseDTO<>(
                        true,
                        "Último estado alcançado.",
                        Map.of("status", solicitation.getStatus().ordinal())
                );
            }
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        catch (IllegalArgumentException e){
            var response = new ApiResponseDTO<>(false, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        catch (Exception e){
            var response = new ApiResponseDTO<>(false, "Falha ao retornar dados da solicitação: " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(
            summary = "Rejeita uma solicitação por ID",
            description = "Rejeita a solicitação informada pelo ID, caso ela exista. Requer autenticação."
    )
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Solicitação rejeitada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Solicitação não encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Usuário não autenticado ou sem permissão",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno ao rejeitar a solicitação",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDTO.class))
            )
    })
    @DeleteMapping("/reject/{id}")
    public ResponseEntity<ApiResponseDTO<?>> rejectSolicitation(@PathVariable Long id){
        try {
            solicitationService.rejectById(id);
            var response = new ApiResponseDTO<>(true, "Solicitação rejeitada com sucesso.", null);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            var response = new ApiResponseDTO<>(false, "Solicitação não encontrada.", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            var response = new ApiResponseDTO<>(false, "Erro ao rejeitar solicitação.", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(
            summary = "Autoriza uma solicitação por ID",
            description = "Autoriza a solicitação informada pelo ID, caso ela exista. Requer autenticação."
    )
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Solicitação autorizada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Solicitação não encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Usuário não autenticado ou sem permissão",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno ao autorizar a solicitação",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDTO.class))
            )
    })
    @PutMapping("/authorize/{id}")
    public ResponseEntity<ApiResponseDTO<?>> authorizeSolicitation(@PathVariable Long id){
        try {
            solicitationService.authorizeById(id);
            var response = new ApiResponseDTO<>(true, "Solicitação autorizada com sucesso.", null);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            var response = new ApiResponseDTO<>(false, "Solicitação não encontrada.", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            var response = new ApiResponseDTO<>(false, "Erro ao autorizar solicitação.", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(
            summary = "Reveter uma autorização de solicitação por ID",
            description = "Reverte autorização a solicitação informada pelo ID, caso ela exista. Requer autenticação."
    )
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Autenticação da solicitação revertida com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Solicitação não encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Usuário não autenticado ou sem permissão",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno ao reverter a autenticação da solicitação",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDTO.class))
            )
    })
    @PutMapping("/revert")
    public ResponseEntity<ApiResponseDTO<?>> revertSolicitationAuthorization(@RequestBody List<Long> solicitationsIds){
        try {
            solicitationService.revertAuthorizationById(solicitationsIds);
            var response = new ApiResponseDTO<>(true, "Autenticação da solicitação revertida com sucesso.", null);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            var response = new ApiResponseDTO<>(false, "Solicitação não encontrada.", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            var response = new ApiResponseDTO<>(false, "Erro ao reverter autenticação de solicitação.", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/get/status/{statusReq}")
    @Operation(
            summary = "Buscar carteirinhas por status",
            description = "Retorna uma lista de solicitações (carteirinhas) com o status informado. Requer autenticação com ADMIN."
    )
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carteirinhas retornadas com sucesso"),
            @ApiResponse(responseCode = "400", description = "Status inválido informado"),
            @ApiResponse(responseCode = "403", description = "Usuário não autenticado ou sem permissão"),
            @ApiResponse(responseCode = "500", description = "Erro interno ao buscar carteirinhas")
    })
    public ResponseEntity<ApiResponseDTO<?>> getSolicitationsByStatus(
            @Parameter(description = "Status da solicitação (EM_ANALISE, PENDENTE, AUTORIZADA)", example = "PENDENTE")
            @PathVariable String statusReq
    ) {
        try {
            SolicitationStatus status = SolicitationStatus.fromString(statusReq);
            List<Map<String, ?>> solicitations = solicitationService.findSolicitationsByStatus(status);
            var response = new ApiResponseDTO<>(
                    true,
                    String.format("%d carteirinhas com status %s retornadas", solicitations.size(), statusReq),
                    solicitations
            );
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            var response = new ApiResponseDTO<>(false, "Status inválido.", null);
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            var response = new ApiResponseDTO<>(false, "Erro interno ao buscar carteirinhas", null);
            return ResponseEntity.internalServerError().body(response);
        }
    }

}
