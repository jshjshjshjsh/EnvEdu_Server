package com.example.demo.jpa.jwt.model;

import com.example.demo.jpa.admin.model.Admin;
import com.example.demo.jpa.user.model.entity.User;
import com.example.demo.jpa.jwt.util.JwtUtil;
import com.example.demo.jpa.security.principal.PrincipalDetails;

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
        claims.put(JwtUtil.claimUsername, user.getUsername());
        claims.put(JwtUtil.claimUserRole, user.getRole().toString());
        this.claims = claims;
    }

    protected JwtToken(String subject, Date expiresAt, PrincipalDetails principalDetails) {
        this.subject = subject;
        this.expiresAt = expiresAt;
        Map<String, String> claims = new HashMap<>();
        claims.put(JwtUtil.claimUsername, principalDetails.getUsername());
        claims.put(JwtUtil.claimUserRole, principalDetails.getRole());
        this.claims = claims;
    }

    protected JwtToken(String subject, Date expiresAt, Admin admin) {
        this.subject = subject;
        this.expiresAt = expiresAt;
        Map<String, String> claims = new HashMap<>();
        claims.put(JwtUtil.claimUsername, admin.getUsername());
        claims.put(JwtUtil.claimUserRole, "ROLE_ADMIN");
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
