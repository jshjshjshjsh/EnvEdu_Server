package com.example.demo.jwt.model;

import com.example.demo.admin.model.Admin;
import com.example.demo.user.model.entity.User;

import java.util.Date;

public class JwtAccessToken extends JwtToken {
    private JwtAccessToken(User user) {
        super("access", new Date(System.currentTimeMillis() + 1800 * 1000), user);
    }

    private JwtAccessToken(Admin admin) {
        super("access", new Date(System.currentTimeMillis() + 1800 * 1000), admin);
    }

    public static JwtAccessToken generateJwtAccessToken(User user) {
        return new JwtAccessToken(user);
    }

    public static JwtAccessToken generateJwtAccessToken(Admin admin) {
        return new JwtAccessToken(admin);
    }
}
