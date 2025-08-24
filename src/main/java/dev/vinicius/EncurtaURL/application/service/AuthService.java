package dev.vinicius.EncurtaURL.application.service;

import dev.vinicius.EncurtaURL.domain.model.User.User;
import dev.vinicius.EncurtaURL.domain.model.User.dto.UserRequestDTO;
import dev.vinicius.EncurtaURL.adapter.out.security.jwt.JwtTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenService jwtTokenService;

    private final Logger log = LoggerFactory.getLogger(AuthService.class);

    public String login(String email, String password) {

        if(email != null && password != null) {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

            log.info("Authentication Successful: {}", authentication.isAuthenticated());

            return jwtTokenService.generateToken(authentication.getName());
        }

        throw new IllegalArgumentException("Email e senha são obrigatórios para login.");

    }

    public User register(UserRequestDTO userRequestDTO) {

        log.info("Register Request: {}", userRequestDTO);

        return userService.createUser(userRequestDTO);
    }

}
