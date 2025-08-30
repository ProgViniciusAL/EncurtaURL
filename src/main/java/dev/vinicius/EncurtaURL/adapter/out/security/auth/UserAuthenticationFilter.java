package dev.vinicius.EncurtaURL.adapter.out.security.auth;

import dev.vinicius.EncurtaURL.domain.model.User.User;
import dev.vinicius.EncurtaURL.adapter.out.repository.SpringDataUserRepository;
import dev.vinicius.EncurtaURL.adapter.out.security.jwt.JwtTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class UserAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private SpringDataUserRepository springDataUserRepository;

    @Autowired
    private JwtTokenService jwtTokenService;

    private final Logger log = LoggerFactory.getLogger(UserAuthenticationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().startsWith("/auth/")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = jwtTokenService.recoverToken(request);

        if(token != null) {
            try {

                log.debug("Token: {}", token);

                String subject = jwtTokenService.getSubjectFromToken(token);
                User user = springDataUserRepository.findByEmail(subject).orElseThrow(() -> new UsernameNotFoundException(subject));
                UserDetailsImpl userDetailsImpl = new UserDetailsImpl(user);

                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetailsImpl, null, userDetailsImpl.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                System.err.println("Invalid token: " + e.getMessage());
            }
        }

        filterChain.doFilter(request, response);

    }

}
