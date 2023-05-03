package com.example.demo.user.controller;

import com.example.demo.cookie.util.CookieUtil;
import com.example.demo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
public class UserLogoutController {
    private final UserService userService;
    @PostMapping("/logout")
    private ResponseEntity<?> logoutHandler(HttpServletResponse response)
    {
        response.setHeader("Set-Cookie", CookieUtil.generateLogoutCookie().toString());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
