package com.example.demo.admin.service;

import com.example.demo.jpa.admin.DTO.AdminLoginDTO;
import com.example.demo.jpa.admin.service.AdminService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AdminServiceTest {
    @Autowired
    private AdminService adminService;

    @Value("${spring.cipher.test.username}")
    private String testUsername;

    @Value("${spring.cipher.test.password}")
    private String testPassword;

    @Test
    void adminLoginTest() {
        AdminLoginDTO adminLoginDTO = new AdminLoginDTO(testUsername, testPassword);
        adminService.loginAdmin(adminLoginDTO);
    }
}
