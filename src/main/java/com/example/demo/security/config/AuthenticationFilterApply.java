package com.example.demo.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Slf4j
public class AuthenticationFilterApply extends AbstractHttpConfigurer<AuthenticationFilterApply, HttpSecurity> {
    @Override
    public void configure(HttpSecurity http) {
        log.info("필터 체크2");

        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        http.addFilter(new AuthenticationFilter(authenticationManager));
    }

    public static AuthenticationFilterApply authenticationFilterApply()
    {
        return new AuthenticationFilterApply();
    }
}
