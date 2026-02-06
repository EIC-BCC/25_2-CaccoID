package br.com.cefet.caccoId.services;

import br.com.cefet.caccoId.dtos.ApiResponseDTO;
import br.com.cefet.caccoId.dtos.AuthenticationDTO;
import br.com.cefet.caccoId.dtos.UserRegisterDTO;
import br.com.cefet.caccoId.infra.security.TokenService;
import br.com.cefet.caccoId.models.User;
import br.com.cefet.caccoId.models.enums.UserRole;
import br.com.cefet.caccoId.repositories.UserRepository;
import org.aspectj.weaver.bcel.ExceptionRange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthenticationService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserRepository userRepository;
    private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);


    public Map<String, Object> login(AuthenticationDTO data){
        var userPassword = new UsernamePasswordAuthenticationToken(data.getEmail(), data.getPassword());
        log.debug("Tentativa de login com e-mail: {}", data.getEmail());
        try {
            var auth = this.authenticationManager.authenticate(userPassword);
            User user = (User) auth.getPrincipal();
            var token = tokenService.generateToken((user));
            return Map.of("role", user.getRole().ordinal(), "token", token);
        }catch (InternalAuthenticationServiceException | BadCredentialsException e){
            log.warn("Falha no login para o e-mail {}: {}", data.getEmail(), e.getMessage());
            return  Map.of();
        }
    }

    public void register(UserRegisterDTO userRegisterDTO, boolean admin) throws  InternalAuthenticationServiceException {
        log.debug("Tentativa de registro para e-mail: {}", userRegisterDTO.getEmail());
        if (this.userRepository.findByEmail(userRegisterDTO.getEmail()) != null) {
            throw new InternalAuthenticationServiceException("Já existe um usuário com esse e-mail.");
        }

        var encryptedPassword = new BCryptPasswordEncoder().encode(userRegisterDTO.getPassword());

        User newUser;
        if(admin){
            newUser = new User(userRegisterDTO.getEmail(), encryptedPassword, UserRole.ADMIN);
            log.info("Novo usuário ADMIN registrado com e-mail: {}", userRegisterDTO.getEmail());
        }
        else{
            newUser = new User(userRegisterDTO.getEmail(), encryptedPassword, UserRole.USER);
            log.info("Novo usuário registrado com e-mail: {}", userRegisterDTO.getEmail());
        }

        this.userRepository.save(newUser);
    }
}
