package com.example.demo.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

@Slf4j
public class AuthorizationFilterApply extends AbstractHttpConfigurer<AuthorizationFilterApply, HttpSecurity> {
    @Override
    public void configure(HttpSecurity http) {
        log.info("필터 체크");
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        http.addFilterAfter(new AuthorizationFilter(authenticationManager), FilterSecurityInterceptor.class);
    }

    public static AuthorizationFilterApply getInstance() {
        return new AuthorizationFilterApply();
    }
}
