package dev.vinicius.EncurtaURL.Service;

import dev.vinicius.EncurtaURL.Domain.User.User;
import dev.vinicius.EncurtaURL.Domain.User.dto.UserRequestDTO;
import dev.vinicius.EncurtaURL.Repository.UserRepository;
import dev.vinicius.EncurtaURL.infra.security.JWT.JwtTokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenService jwtTokenService;

    public User getCurrentUser(HttpServletRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = authentication.getPrincipal() != null ? (User) authentication.getPrincipal() : null;
        if(currentUser != null) {
            return userRepository.findById(currentUser.getId()).orElse(null);
        }
        return null;

    }

    public User createUser(UserRequestDTO userDTO) {

        if(!userRepository.findByUsername(userDTO.username()).isPresent()) {
            User user = new User(userDTO, passwordEncoder);
            jwtTokenService.generateToken(user.getEmail());
            return userRepository.save(user);
        }

        throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");

    }

}
