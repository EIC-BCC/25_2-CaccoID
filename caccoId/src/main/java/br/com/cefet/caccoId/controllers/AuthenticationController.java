package br.com.cefet.caccoId.controllers;

import br.com.cefet.caccoId.dtos.ApiResponseDTO;
import br.com.cefet.caccoId.dtos.AuthenticationDTO;
import br.com.cefet.caccoId.dtos.UserRegisterDTO;
import br.com.cefet.caccoId.repositories.UserRepository;
import br.com.cefet.caccoId.services.AuthenticationService;
import br.com.cefet.caccoId.services.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("auth")
@Tag(name = "Gerenciamento do Autenticação", description = "Endpoints para gerenciar a autenticação")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private EmailService emailService;

    @Operation(
            summary = "Login de usuário",
            description = "Autentica o usuário com e-mail e senha, retornando um token JWT em caso de sucesso. Requisição no formato JSON."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário autenticado com sucesso, token JWT retornado."),
            @ApiResponse(responseCode = "400", description = "Falha na autenticação. Verifique e-mail e senha."),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    })
    @PostMapping("/login")
    public HttpEntity<ApiResponseDTO<?>> login(@Valid @RequestBody AuthenticationDTO data) {
        var userData = this.authenticationService.login(data);

        var token = (String) userData.getOrDefault("token", "");

        if (!token.isBlank()) {
            var response = new ApiResponseDTO<>(true, "Usuário válido.", userData);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            var response = new ApiResponseDTO<>(false, "Não foi possível realizar o login. Verifique seus dados e tente novamente.", Map.of("token", ""));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @Operation(
            summary = "Registro de novo usuário",
            description = "Registra um novo usuário no sistema com base nos dados fornecidos. Retorna sucesso ou falha caso o e-mail já esteja em uso."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário registrado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Já existe um usuário com esse e-mail."),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    })
    @PostMapping("/register")
    public ResponseEntity<ApiResponseDTO<Object>> register(@Valid @RequestBody UserRegisterDTO userRegisterDTO){
        try {
            authenticationService.register(userRegisterDTO, false);
            var response = new ApiResponseDTO<>(true, "Usuário registrado com sucesso.", null);
            String email = userRegisterDTO.getEmail();
            emailService.sendEmail(email, "Usuário cadastrado!", "Seu cadastro foi realizado com sucesso! Faça login para solicitar sua carteirinha.");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (InternalAuthenticationServiceException e){
            var response = new ApiResponseDTO<>(false, "Já existe um usuário com esse e-mail.", null);
            return ResponseEntity.badRequest().body(response);
        }
    }

    @Operation(
            summary = "Registro de administrador",
            description = "Registra um novo administrador no sistema com base nos dados fornecidos. Requer autenticação com perfil ADMIN."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Administrador registrado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Já existe um usuário com esse e-mail."),
            @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário autenticado não possui permissão."),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    })
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/register-admin")
    public ResponseEntity<ApiResponseDTO<Object>> createAdmin(@Valid @RequestBody UserRegisterDTO userRegisterDTO){
        try {
            authenticationService.register(userRegisterDTO, true);
            var response = new ApiResponseDTO<>(true, "Administrador registrado com sucesso.", null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (InternalAuthenticationServiceException e){
            var response = new ApiResponseDTO<>(false, "Já existe um usuário com esse e-mail.", null);
            return ResponseEntity.badRequest().body(response);
        }
    }
}
