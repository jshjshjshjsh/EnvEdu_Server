package com.example.demo.admin.service;

import com.example.demo.admin.DTO.AdminLoginDTO;
import com.example.demo.admin.cipher.AdminCipher;
import com.example.demo.admin.model.Admin;
import com.example.demo.admin.repository.AdminRepository;
import com.example.demo.jwt.model.JwtAccessToken;
import com.example.demo.jwt.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class AdminService {
    @Value("${spring.server.domain}")
    private String domain;

    private final AdminCipher adminCipher;

    private final AdminRepository adminRepository;

    public ResponseCookie loginAdmin(AdminLoginDTO adminLoginDTO) {
        Admin admin = adminRepository.findByUsername(adminLoginDTO.getUsername()).orElseThrow(()->new IllegalArgumentException("로그인 정보가 일치하지 않습니다"));
        if(!adminCipher.encrypt(adminLoginDTO.getPassword()).equals(admin.getPassword())) {
            throw new IllegalArgumentException("로그인 정보가 일치하지 않습니다");
        }

        JwtAccessToken jwtAccessToken = JwtAccessToken.generateJwtAccessToken(admin);
        ResponseCookie cookie = ResponseCookie.from("access_token", JwtUtil.convertJwtToString(jwtAccessToken))
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .maxAge(Duration.ofSeconds(1000000))
                .path("/")
                .build();

        return cookie;
    }
}
