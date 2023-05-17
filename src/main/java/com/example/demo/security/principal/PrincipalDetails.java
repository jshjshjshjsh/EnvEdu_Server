package com.example.demo.security.principal;

import org.springframework.security.core.userdetails.UserDetails;

public interface PrincipalDetails extends UserDetails {
    String getRole();
}
