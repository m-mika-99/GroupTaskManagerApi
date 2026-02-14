package com.example.GroupTaskManagerApi.infra.security.jwt;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtTokenProvider {

    // TODO configから取得
    private final String secret = "my-special-secret-keymy-special-secret-keymy-special-secret-keymy-special-secret-key";
    private final long validityMillis = 1000/*[ms/s]*/ * 60/*[s/min]*/ * 60/*[min/h]*/ * 1/*[h]*/;

    private Key getSigningKey () {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String createToken (UUID userId) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + validityMillis);

        return Jwts.builder()
                .setSubject(userId.toString())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(getSigningKey())
                .compact();
    }

    public UUID getUserId (String token) {
        String subject = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return UUID.fromString(subject);
    }

    public boolean validate (String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
