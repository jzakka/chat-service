package com.example.chatservice.utils;

import com.example.chatservice.vo.TokenJoinAuthority;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtUtils { ;
    private final Environment env;
    private final ObjectMapper objectMapper;

    public Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(env.getProperty("token.secret"))
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

    public List<TokenJoinAuthority> getJoinAuthorities(String token) {
        if (token.startsWith("Bearer")) {
            token = token.replace("Bearer", "");
        }

        List<TokenJoinAuthority> gatherIds = objectMapper.convertValue(getClaims(token).get("gatherIds"), new TypeReference<>() {});

        return gatherIds;
    }
}
