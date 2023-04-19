package com.example.demo.jwt.model;

import com.example.demo.admin.model.Admin;
import com.example.demo.user.model.entity.User;

import java.util.Date;

public class JwtRefreshToken extends JwtToken {
    private JwtRefreshToken(User user) {
        super("refresh", new Date(System.currentTimeMillis() + 86400 * 1000), user);
    }

    private JwtRefreshToken(Admin admin) {
        super("refresh", new Date(System.currentTimeMillis() + 86400 * 1000), admin);
    }

    public static JwtRefreshToken generateJwtRefreshToken(User user) {
        return new JwtRefreshToken(user);
    }

    public static JwtRefreshToken generateJwtRefreshToken(Admin admin) {
        return new JwtRefreshToken(admin);
    }
}
