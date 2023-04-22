package com.example.demo.cookie.util;

import com.example.demo.jwt.model.JwtRefreshToken;
import com.example.demo.jwt.util.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

@Component
public class CookieUtil {
    public static String seedDomain;

    @Value("${spring.server.domain}")
    private String domain;

    @PostConstruct
    private void init() {
        seedDomain = domain;
    }

    public static ResponseCookie generateCookieForRefreshToken(JwtRefreshToken refreshToken) {
        return ResponseCookie.from(JwtRefreshToken.tokenName, URLEncoder.encode(JwtUtil.convertJwtToString(refreshToken), StandardCharsets.UTF_8))
                .domain(seedDomain)
                .secure(true)
                .httpOnly(true)
                .sameSite("Strict")
                .path("/")
                .maxAge(Duration.ofSeconds(JwtRefreshToken.validTimeInSec))
                .build();
    }
}
