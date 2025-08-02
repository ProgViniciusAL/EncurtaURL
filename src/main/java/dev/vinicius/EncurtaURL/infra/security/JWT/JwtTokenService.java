package dev.vinicius.EncurtaURL.infra.security.JWT;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class JwtTokenService {

    @Value("${host.jwt.secret}")
    private String jwt_secret;

    private static final String issuer = "curturl-auth";

    public String generateToken(String username) {

        try {

            Algorithm algorithm = Algorithm.HMAC256(jwt_secret);

            return JWT
                    .create()
                    .withIssuer(issuer)
                    .withIssuedAt(generateIssuedAt())
                    .withExpiresAt(generateExpirationDate())
                    .withSubject(username)
                    .sign(algorithm);

        } catch(JWTCreationException exception) {
            throw new JWTCreationException("Error while generating token",  exception);
        }

    }

    public String recoverToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");

        if(header != null && header.startsWith("Bearer ")) {
            return header.replace("Bearer ", "");
        }

        return null;
    }

    public String getSubjectFromToken(String token) {

        try {

            Algorithm algorithm = Algorithm.HMAC256(jwt_secret);

            return JWT
                    .require(algorithm)
                    .withIssuer(issuer)
                    .build()
                    .verify(token)
                    .getSubject();

        } catch(JWTVerificationException exception) {
            throw new JWTVerificationException("Error while verifying token",  exception);
        }

    }

    private Instant generateExpirationDate() {
        return Instant.now().plusSeconds(3600);
    }

    private Instant generateIssuedAt() {
        return Instant.now();
    }

}
