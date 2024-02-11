package com.lsm.task.infrastructure;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtTokenProvider {
    @Value("${security.jwt.token.secret-key}")
    private String SECRET_KEY;
    @Value("${security.jwt.token.expire-minute}")
    private long VALIDITY_IN_MINUTES;

    public Key getSigningKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String payload) {
        Claims claims = Jwts.claims().setSubject(payload);
        Date now = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        Date validity = Date.from(LocalDateTime.now().plusMinutes(VALIDITY_IN_MINUTES).atZone(ZoneId.systemDefault()).toInstant());

        return Jwts.builder()
                   .setClaims(claims)
                   .signWith(getSigningKey(SECRET_KEY), SignatureAlgorithm.HS256)
                   .setIssuedAt(now)
                   .setExpiration(validity)
                   .compact();
    }

    public String getPayload(String token) {
        return Jwts.parserBuilder()
                   .setSigningKey(getSigningKey(SECRET_KEY))
                   .build()
                   .parseClaimsJws(token)
                   .getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                                     .setSigningKey(getSigningKey(SECRET_KEY))
                                     .build()
                                     .parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
