package com.example.demo.admin.controller;

import com.example.demo.admin.DTO.AdminLoginDTO;
import com.example.demo.admin.service.AdminService;
import com.example.demo.jwt.model.JwtAccessToken;
import com.example.demo.jwt.model.JwtRefreshToken;
import com.example.demo.jwt.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/login/admin")
    private ResponseEntity<?> loginAdmin(@RequestBody AdminLoginDTO adminLoginDTO, HttpServletResponse response) {
        response.setHeader("Set-Cookie", adminService.loginAdmin(adminLoginDTO).toString());
        response.setHeader(JwtUtil.headerString, JwtUtil.convertJwtToString(JwtAccessToken.generateJwtAccessToken(
                adminService.findAdminByUsername(adminLoginDTO.getUsername())
        )));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 현재는 사용되고 있지 않음
     * 일반 유저의 로그아웃 api 공유 중
     */
    @PostMapping("/logout/admin")
    private ResponseEntity<?> logoutAdmin(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies) {
            if(cookie.getName().equals(JwtRefreshToken.tokenName) || cookie.getName().equals(JwtAccessToken.tokenName)) {
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    private ResponseEntity<?> illegalArgumentExceptionHandler(IllegalArgumentException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
