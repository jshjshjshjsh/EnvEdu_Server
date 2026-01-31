package com.example.demo.datacontrol.dataclassroom.repository;

import com.example.demo.datacontrol.dataclassroom.domain.entity.ClassroomClass;
import com.example.demo.datacontrol.dataclassroom.domain.types.ClassroomDataType;
import com.example.demo.datacontrol.dataclassroom.domain.types.ClassroomStudentGrade;
import com.example.demo.datacontrol.dataclassroom.domain.types.ClassroomSubjectType;
import com.example.demo.user.model.entity.Educator;
import com.example.demo.user.model.enumerate.Gender;
import com.example.demo.user.model.enumerate.IsAuthorized;
import com.example.demo.user.model.enumerate.Role;
import com.example.demo.user.model.enumerate.State;
import com.example.demo.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ClassroomClassRepositoryTest {

    @Autowired
    private ClassroomClassRepository classroomClassRepository;

    @Autowired
    private UserRepository userRepository;

    @Container
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("enveduserver")
            .withUsername("root")
            .withPassword("root");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
        registry.add("spring.datasource.driver-class-name", mysql::getDriverClassName);
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @DisplayName("Maven 환경에서도 MySQL 컨테이너가 잘 뜨는지 확인")
    void checkMysqlVersion() {
        String result = jdbcTemplate.queryForObject("SELECT VERSION()", String.class);

        System.out.println(">>> 실행된 MySQL 버전: " + result);

        assertThat(result).startsWith("8.0");
    }
}