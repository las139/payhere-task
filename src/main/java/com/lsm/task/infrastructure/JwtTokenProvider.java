package com.lsm.task.infrastructure;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {
    public static final String DEFAULT_USER_TYPE = "ROLE_USER";

    @Value("${security.jwt.token.secret-key}")
    private String SECRET_KEY;
    @Value("${security.jwt.token.expire-minute}")
    private long VALIDITY_IN_MINUTES;

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                   .setSigningKey(getSigningKey(SECRET_KEY))
                   .build()
                   .parseClaimsJws(token)
                   .getBody();
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
        return extractAllClaims(token).getSubject();
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

    private Key getSigningKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = getUserDetails(token);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private UserDetails getUserDetails(String token) {
        Claims claims = extractAllClaims(token);
        String username = claims.getSubject();

        // 과제에서는 단순화를 위해 "ROLE_USER" 권한만 설정
        return new User(username, "", Collections.singleton(new SimpleGrantedAuthority(DEFAULT_USER_TYPE)));
    }
}
