package com.example.demo.admin.service;

import com.example.demo.admin.DTO.AdminLoginDTO;
import com.example.demo.admin.cipher.AdminCipher;
import com.example.demo.admin.model.Admin;
import com.example.demo.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminCipher adminCipher;

    private final AdminRepository adminRepository;

    public void loginAdmin(AdminLoginDTO adminLoginDTO) {
        Admin admin = adminRepository.findByUsername(adminLoginDTO.getUsername()).orElseThrow(()->new IllegalArgumentException("로그인 정보가 일치하지 않습니다"));
        if(!adminCipher.encrypt(adminLoginDTO.getPassword()).equals(admin.getPassword())) {
            throw new IllegalArgumentException("로그인 정보가 일치하지 않습니다");
        }
    }
}
