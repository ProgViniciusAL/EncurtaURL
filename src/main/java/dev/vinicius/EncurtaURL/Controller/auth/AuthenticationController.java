package dev.vinicius.EncurtaURL.Controller.auth;

import dev.vinicius.EncurtaURL.Domain.User.User;
import dev.vinicius.EncurtaURL.Domain.User.dto.RegisterResponseDTO;
import dev.vinicius.EncurtaURL.Domain.User.dto.UserRequestDTO;
import dev.vinicius.EncurtaURL.Domain.User.dto.LoginResponseDTO;
import dev.vinicius.EncurtaURL.Service.AuthService;
import dev.vinicius.EncurtaURL.Domain.User.dto.LoginDTO;
import dev.vinicius.EncurtaURL.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthService authService;

    private final Logger log = LoggerFactory.getLogger(AuthenticationController.class);
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO) {

        log.info("Login Request: {}", loginDTO);
        String token = authService.login(loginDTO.email(), loginDTO.password());
        log.info("Login Success: {}", token);
        return ResponseEntity.ok(new LoginResponseDTO(token));

    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@RequestBody UserRequestDTO userRequestDTO) {

        log.info("Register Request: {}", userRequestDTO);
        User user = authService.register(userRequestDTO);

        return ResponseEntity.ok(new RegisterResponseDTO(user.getUsername(), user.getEmail()));

    }

}
