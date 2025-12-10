package com.minegocio.backend.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expirationMillis;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generarToken(String subject, String rol) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + expirationMillis);

        return Jwts.builder()
                .setSubject(subject)
                .claim("roles", List.of("ROLE_" + rol))
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String obtenerSubjectDesdeToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validarToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException ex) {
            System.out.println("Token expirado: " + ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            System.out.println("Token no soportado: " + ex.getMessage());
        } catch (MalformedJwtException ex) {
            System.out.println("Token mal formado: " + ex.getMessage());
        } catch (SignatureException ex) {
            System.out.println("Firma inválida: " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            System.out.println("Token vacío o nulo: " + ex.getMessage());
        }
        return false;
    }

    public long getExpirationMillis() {
        return expirationMillis;
    }
}
