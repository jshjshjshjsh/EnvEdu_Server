package com.example.demo.jpa.jwt.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.example.demo.jpa.exceptions.NoJwtTokenContainedException;
import com.example.demo.jpa.jwt.model.JwtToken;
import com.example.demo.jpa.jwt.model.JwtRefreshToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
public class JwtUtil {
    public static String jwtKey;

    public static final String headerString = "Authorization";

    public static final String tokenType = "Bearer ";

    public static final String claimName = "user_info";

    public static final String claimUsername = "username";

    public static final String claimUserRole = "role";

    @Value("${spring.jwt.key}")
    private String key;

    public static String convertJwtToString(JwtToken jwtToken) {
        return tokenType + JWT.create()
                        .withSubject(jwtToken.getSubject())
                        .withExpiresAt(jwtToken.getExpiresAt())
                        .withClaim(claimName, jwtToken.getClaims())
                        .sign(Algorithm.HMAC512(jwtKey));
    }

    public static Map<String, Claim> getJwtRefreshTokenFromCookieAndParse(Cookie[] cookies) throws JWTVerificationException {
        for(Cookie cookie : cookies) {
            if(cookie.getName().equals(JwtRefreshToken.tokenName)) {
                String token = URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8).replace(JwtUtil.tokenType,"");
                return JWT.require(Algorithm.HMAC512(jwtKey)).build().verify(token).getClaims();
            }
        }
        throw new NoJwtTokenContainedException();
    }

    public static Algorithm getAlgorithm() {
        return Algorithm.HMAC512(jwtKey);
    }

    @PostConstruct
    private void init() {
        jwtKey = key;
    }
}
