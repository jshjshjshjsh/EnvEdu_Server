package com.example.demo;

import com.example.demo.admin.cipher.AdminCipher;
import com.example.demo.admin.model.Admin;
import com.example.demo.user.dto.request.RegisterDTO;
import com.example.demo.user.model.entity.Student;
import com.example.demo.user.model.enumerate.Gender;
import com.example.demo.user.model.enumerate.Role;
import com.example.demo.user.model.enumerate.State;
import com.example.demo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Date;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class InitDB implements CommandLineRunner {

    @PersistenceContext
    private final EntityManager em;
    private final AdminCipher adminCipher;
    private final UserService userService;

    @Override
    public void run(String... args) throws Exception {
        init();
    }

    public void init() {
        log.info("InitSampleDB.init");

        Admin admin = new Admin("admin", adminCipher.encrypt("1234"));
        em.persist(admin);

        userService.addUser(new RegisterDTO("Student1", "1234", "example@google.com",
                Gender.MALE, Role.ROLE_STUDENT,Date.valueOf("1999-04-21")));


    }
}