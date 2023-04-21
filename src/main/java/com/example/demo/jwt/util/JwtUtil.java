package com.example.demo.jwt.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.demo.jwt.model.JwtToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class JwtUtil {
    public static String jwtKey;

    @Value("${spring.jwt.key}")
    private String key;

    public static String convertJwtToString(JwtToken jwtToken) {
        return JWT.create()
                        .withSubject(jwtToken.getSubject())
                        .withExpiresAt(jwtToken.getExpiresAt())
                        .withClaim("user_info", jwtToken.getClaims())
                        .sign(Algorithm.HMAC512(jwtKey));
    }

    @PostConstruct
    private void init() {
        jwtKey = key;
    }
}
