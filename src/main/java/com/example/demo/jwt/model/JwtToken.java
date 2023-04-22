package com.example.demo.jwt.model;

import com.example.demo.admin.model.Admin;
import com.example.demo.security.principal.PrincipalDetails;
import com.example.demo.user.model.entity.User;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public abstract class JwtToken {
    private final String subject;

    private final Date expiresAt;

    private final Map<String, String> claims;

    protected JwtToken(String subject, Date expiresAt, User user) {
        this.subject = subject;
        this.expiresAt = expiresAt;
        Map<String, String> claims = new HashMap<>();
        claims.put("username", user.getUsername());
        claims.put("role", user.getRole().toString());
        this.claims = claims;
    }

    protected JwtToken(String subject, Date expiresAt, PrincipalDetails principalDetails) {
        this.subject = subject;
        this.expiresAt = expiresAt;
        Map<String, String> claims = new HashMap<>();
        claims.put("username", principalDetails.getUsername());
        claims.put("role", principalDetails.getRole());
        this.claims = claims;
    }

    protected JwtToken(String subject, Date expiresAt, Admin admin) {
        this.subject = subject;
        this.expiresAt = expiresAt;
        Map<String, String> claims = new HashMap<>();
        claims.put("username", admin.getUsername());
        claims.put("role", "ROLE_ADMIN");
        this.claims = claims;
    }

    public String getSubject() {
        return this.subject;
    }

    public Date getExpiresAt() {
        return this.expiresAt;
    }

    public Map<String, String> getClaims() {
        return this.claims;
    }
}
