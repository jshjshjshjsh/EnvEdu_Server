package com.example.demo.admin.DTO;

import lombok.Getter;

@Getter
public class AdminLoginDTO {
    private String username;
    private String password;

    public AdminLoginDTO() {}

    public AdminLoginDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
