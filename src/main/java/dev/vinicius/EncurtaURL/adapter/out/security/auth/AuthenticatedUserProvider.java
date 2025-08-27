package dev.vinicius.EncurtaURL.adapter.out.security.auth;

import dev.vinicius.EncurtaURL.adapter.out.repository.UserRepository;
import dev.vinicius.EncurtaURL.domain.model.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AuthenticatedUserProvider {

    @Autowired
    private UserRepository userRepository;

    public User getUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Authenticated user not found"));

    }

}
