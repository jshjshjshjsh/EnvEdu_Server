package com.example.demo.jpa.jwt.model;

import com.example.demo.jpa.admin.model.Admin;
import com.example.demo.jpa.user.model.entity.User;
import com.example.demo.jpa.security.principal.PrincipalDetails;

import java.util.Date;

public class JwtRefreshToken extends JwtToken {
    public final static String tokenName = "refresh_token";

    public final static int validTimeInSec = 86400;

    private JwtRefreshToken(User user) {
        super("refresh", new Date(System.currentTimeMillis() + validTimeInSec * 1000L), user);
    }

    private JwtRefreshToken(PrincipalDetails principalDetails) {
        super("refresh", new Date(System.currentTimeMillis() + validTimeInSec * 1000L), principalDetails);
    }

    private JwtRefreshToken(Admin admin) {
        super("refresh", new Date(System.currentTimeMillis() + validTimeInSec * 1000L), admin);
    }

    public static JwtRefreshToken generateJwtRefreshToken(User user) {
        return new JwtRefreshToken(user);
    }

    public static JwtRefreshToken generateJwtRefreshToken(PrincipalDetails principalDetails) {
        return new JwtRefreshToken(principalDetails);
    }

    public static JwtRefreshToken generateJwtRefreshToken(Admin admin) {
        return new JwtRefreshToken(admin);
    }
}
