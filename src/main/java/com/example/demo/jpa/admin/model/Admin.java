package com.example.demo.jpa.admin.model;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 20, unique = true)
    private String username;

    @Column(nullable = false, length = 100)
    private String password;

    public Admin() {}

    @Builder(builderMethodName = "adminBuilder")
    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
