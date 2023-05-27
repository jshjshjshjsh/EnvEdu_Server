package com.example.demo.user.controller;

import com.example.demo.jwt.model.JwtRefreshToken;
import com.example.demo.jwt.util.JwtUtil;
import com.example.demo.user.dto.request.RegisterDTO;
import com.example.demo.user.model.entity.User;
import com.example.demo.user.model.enumerate.Gender;
import com.example.demo.user.model.enumerate.Role;
import com.example.demo.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.Cookie;
import java.sql.Date;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerTest {
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserRegisterController userRegisterController;

    @Autowired
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MockMvc userMock;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private String testEducatorName;

    @BeforeEach
    public void setup() {
        this.testEducatorName = "testEducator";
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        userMock = MockMvcBuilders
                .standaloneSetup(userRegisterController)
                .build();
    }

    @Test
    void userLoginTest() throws Exception {
        RegisterDTO registerDTO = new RegisterDTO("test" ,"test", "'test@naver.com", Gender.MALE, Role.ROLE_STUDENT, new Date(100));
        addSomeUserRecords(registerDTO);
        userService.addUser(registerDTO);

        String loginRequestBody = "{" +
                "\"username\": \"" + "test" + "\"," +
                "\"password\": \"" + "test" + "\"" +
                "}";
        mockMvc.perform(post("/login")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginRequestBody)).andDo(print());
    }

    @Test
    public void duplicateStudent_educatorInsertTest() throws Exception {
        RegisterDTO educatorDTO = new RegisterDTO(
                testEducatorName,
                "testPassword",
                "testEducatorEmail@naver.com",
                Gender.MALE,
                Role.ROLE_EDUCATOR,
                Date.valueOf(LocalDate.now()));

        addSomeUserRecords(
                educatorDTO,
                new RegisterDTO(
                        "testStudent1",
                        "testPassword",
                        "testStudent1Email@naver.com",
                        Gender.MALE,
                        Role.ROLE_STUDENT,
                        Date.valueOf(LocalDate.now())),
                new RegisterDTO(
                        "testStudent2",
                        "testPassword",
                        "testStudent2Email@naver.com",
                        Gender.MALE,
                        Role.ROLE_STUDENT,
                        Date.valueOf(LocalDate.now()))
        );

        String requestBody = "{" +
                    "\"studentUsernames\": [\"testStudent1\", \"testStudent2\"]" +
                "}";

        Cookie cookie = generateCookie(JwtRefreshToken.tokenName, educatorDTO);

        userMock.perform(post("/educator/student")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .cookie(cookie)
                .content(requestBody)
        ).andExpectAll(status().is2xxSuccessful()).andDo(print());

        userMock.perform(post("/educator/student")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .cookie(cookie)
                .content(requestBody)
        ).andExpectAll(status().is2xxSuccessful()).andDo(print());

        userMock.perform(get("/educator/student_educator")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .cookie(cookie)
        ).andDo(print());

    }

    private void addSomeUserRecords(RegisterDTO... registerDTOs) {
        for(RegisterDTO registerDTO : registerDTOs) {
            userService.addUser(registerDTO);
        }
    }

    private Cookie generateCookie(String name, RegisterDTO registerDTO) {
        User user = registerDTO.getRole().generateUserByRole(registerDTO, bCryptPasswordEncoder);
        return new Cookie(name, JwtUtil.convertJwtToString(JwtRefreshToken.generateJwtRefreshToken(user)));
    }
}
