package com.postech.parquimetro.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.postech.parquimetro.domain.customer.Customer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class TokenService {

    @Value("${api.security.token.secret}") //variavel disponivel no application.properties
    private String secret;

    public String newToken(Customer customer) {
        try {
            var algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("API parquimetro")
                    .withSubject(customer.getLogin())
                    //.withClaim("customerID", customer.getId())
                    .withExpiresAt(dateTimeExpiration())
                    .sign(algorithm);
        } catch (JWTVerificationException exception){
            throw new RuntimeException("error during the generation a new token", exception);
        }
    }

    public String getSubject(String tokenJWT) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                    .withIssuer("API parquimetro")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Token JWT invalid or expirated!");
        }
    }

    private Instant dateTimeExpiration() {
        return LocalDateTime.now()
                .plusHours(2)
                .atZone(ZoneId.of("Europe/Paris"))
                .toInstant();
    }
}