package com.example.chatservice.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {
    @Value("${token.secret}")
    private String secretKey;

    public Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    @NotNull
    public String getMemberId(String token) {
        if (token.startsWith("Bearer")) {
            token = token.replace("Bearer", "");
        }
        return getClaims(token).getSubject();
    }
}
