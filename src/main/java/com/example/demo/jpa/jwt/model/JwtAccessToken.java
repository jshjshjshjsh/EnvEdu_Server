package com.example.demo.jpa.jwt.model;

import com.example.demo.jpa.admin.model.Admin;
import com.example.demo.jpa.user.model.entity.User;
import com.example.demo.jpa.security.principal.PrincipalDetails;

import java.util.Date;

public class JwtAccessToken extends JwtToken {
    public final static String tokenName = "access_token";

    public final static int validTimeInSec = 180;

    private JwtAccessToken(User user) {
        super("access", new Date(System.currentTimeMillis() + validTimeInSec * 1000L), user);
    }

    private JwtAccessToken(PrincipalDetails principalDetails) {
        super("access", new Date(System.currentTimeMillis() + validTimeInSec * 1000L), principalDetails);
    }

    private JwtAccessToken(Admin admin) {
        super("access", new Date(System.currentTimeMillis() + validTimeInSec * 1000L), admin);
    }

    public static JwtAccessToken generateJwtAccessToken(User user) {
        return new JwtAccessToken(user);
    }

    public static JwtAccessToken generateJwtAccessToken(PrincipalDetails principalDetails) {
        return new JwtAccessToken(principalDetails);
    }

    public static JwtAccessToken generateJwtAccessToken(Admin admin) {
        return new JwtAccessToken(admin);
    }
}
