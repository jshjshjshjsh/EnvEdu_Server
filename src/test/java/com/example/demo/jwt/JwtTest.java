package com.example.demo.jwt;

import com.example.demo.jpa.admin.model.Admin;
import com.example.demo.jpa.jwt.model.JwtAccessToken;
import com.example.demo.jpa.jwt.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class JwtTest {
    @Test
    void jwtGenerationTest() {
        Admin admin = Admin.adminBuilder()
                .username("test")
                .password("test")
                .build();
        JwtAccessToken accessToken = JwtAccessToken.generateJwtAccessToken(admin);
        log.info(JwtUtil.convertJwtToString(accessToken));
    }
}
