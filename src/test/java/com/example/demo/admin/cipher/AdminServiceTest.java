package com.example.demo.admin.cipher;

import com.example.demo.admin.model.Admin;
import com.example.demo.admin.repository.AdminRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class AdminServiceTest {
    @Autowired
    private AdminCipher adminCipher;

    @Autowired
    private AdminRepository adminRepository;

    @Test
    @Transactional
    public void encryptionTest() {
        Admin admin = Admin.adminBuilder()
                .username("admin")
                .password(adminCipher.encrypt("testpassword"))
                .build();
        adminRepository.save(admin);

        String encrypted = adminCipher.encrypt("testpassword");

        Admin fetched = adminRepository.findByUsername("admin").orElseThrow(IllegalArgumentException::new);
        Assertions.assertEquals(encrypted, fetched.getPassword());
    }
}
