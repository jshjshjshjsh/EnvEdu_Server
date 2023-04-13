package com.example.demo.admin.controller;

import com.example.demo.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
}
