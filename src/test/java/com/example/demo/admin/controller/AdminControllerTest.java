package com.example.demo.admin.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.Cookie;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
public class AdminControllerTest {
    @Autowired
    private AdminController adminController;

    @Value("${spring.cipher.test.username}")
    private String testUsername;

    @Value("${spring.cipher.test.password}")
    private String testPassword;

    private MockMvc adminMockMvc;

    @BeforeEach
    public void setup() {
        adminMockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
    }

    @Test
    void adminLoginControllerTest() throws Exception {
        String loginRequestBody = "{" +
                "\"username\": \"" + testUsername + "\"," +
                "\"password\": \"" + testPassword + "\"" +
                "}";
        adminMockMvc.perform(post("/login/admin")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginRequestBody)).andDo(print());
    }

    @Test
    void adminLogoutControllerTest() throws Exception {
        Cookie accessCookie = new Cookie("access_token", "test");
        Cookie refreshCookie = new Cookie("refresh_token", "test");
        adminMockMvc.perform(post("/logout/admin")
                .cookie(accessCookie)
                .cookie(refreshCookie)).andDo(print());
    }
}
