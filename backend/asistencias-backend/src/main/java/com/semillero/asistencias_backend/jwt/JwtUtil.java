package com.semillero.asistencias_backend.jwt;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component //para poder inyectarlo luego en JWTfilter --ojo con esto
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    //Generar token con Roles
    public String generateToken(String username, List<String> roles) {
        return JWT.create()
                .withSubject(username) // Quién es el token (el usuario)
                .withClaim("roles", roles)// Datos adicionales (roles del usuario)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 120 * 60 * 1000))//expira en 2 horas 
                .sign(Algorithm.HMAC256(secret));
    }
    //Valida Token

    public boolean validateToken(String token, String username) {
        try {
            DecodedJWT jwt = getDecodedJWT(token);
            //el nombre de usuario dentro del token) coincida con el usuario que se espera.
            // y tiempo de expiración
            return jwt.getSubject().equals(username) && jwt.getExpiresAt().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    //extraer el nombre de usuario
    public String getUsername(String token) {
        return getDecodedJWT(token).getSubject();
    }

    //lista de roles
    public List<String> getRoles(String token) {
        return getDecodedJWT(token).getClaim("roles").asList(String.class);
    }

    private DecodedJWT getDecodedJWT(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
        return verifier.verify(token);
    }

}
