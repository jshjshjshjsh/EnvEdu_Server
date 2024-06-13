package com.example.demo.security.config;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

public class AuthenticationFilterApply extends AbstractHttpConfigurer<AuthenticationFilterApply, HttpSecurity> {
    @Override
    public void configure(HttpSecurity http) {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        http.addFilter(new AuthenticationFilter(authenticationManager));
    }

    public static AuthenticationFilterApply authenticationFilterApply()
    {
        return new AuthenticationFilterApply();
    }
}
