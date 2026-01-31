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

    @Test
    @DisplayName("리팩토링 전 Classroom 가져오는 문제 코드")
    void findAllByOwner() {
        // given (상황 설정)
        // 1. 교사(User) 데이터 생성 및 저장
        // (User는 추상 클래스이므로 구현체인 Educator를 생성)
        Educator teacher = Educator.educatorBuilder()
                .username("teacher1")
                .password("password123")
                .email("teacher@test.com")
                .role(Role.ROLE_EDUCATOR)
                .gender(Gender.MALE)
                .state(State.ACTIVE)
                .isAuthorized(IsAuthorized.YES)
                .birthday(java.sql.Date.valueOf(LocalDate.now()))
                .build();

        // 2. 비교군이 될 다른 교사 생성 (이 사람 데이터는 조회되면 안 됨)
        Educator otherTeacher = Educator.educatorBuilder()
                .username("other1")
                .password("password123")
                .email("other@test.com")
                .role(Role.ROLE_EDUCATOR)
                .gender(Gender.FEMALE)
                .state(State.ACTIVE)
                .isAuthorized(IsAuthorized.YES)
                .birthday(java.sql.Date.valueOf(LocalDate.now()))
                .build();

        userRepository.saveAll(List.of(teacher, otherTeacher));

        // 3. teacher의 클래스 2개 생성
        ClassroomClass class1 = new ClassroomClass(ClassroomStudentGrade.HI1, ClassroomSubjectType.MATH, ClassroomDataType.SEED,
                "수학 1반", "기초", "설명", teacher, "thumb1.jpg");

        ClassroomClass class2 = new ClassroomClass(ClassroomStudentGrade.HI1, ClassroomSubjectType.EARTH_SCIENCE, ClassroomDataType.SEED,
                "과학 1반", "심화", "설명", teacher, "thumb2.jpg");

        // 4. otherTeacher의 클래스 1개 생성 (노이즈 데이터)
        ClassroomClass otherClass = new ClassroomClass(ClassroomStudentGrade.MD1, ClassroomSubjectType.CHEMISTRY, ClassroomDataType.SEED,
                "영어 1반", "기초", "설명", otherTeacher, "thumb3.jpg");

        classroomClassRepository.saveAll(List.of(class1, class2, otherClass));

        // when (테스트 실행)
        // teacher가 주인이 클래스만 찾아오라고 시킴
        List<ClassroomClass> result = classroomClassRepository.findAllByOwner(teacher);

        // then (검증)
        // 1. 개수가 2개여야 함 (otherClass는 제외)
        assertThat(result).hasSize(2);

        // 2. 조회된 클래스의 제목이 내가 넣은 것과 일치하는지 확인
        assertThat(result).extracting("title")
                .containsExactlyInAnyOrder("수학 1반", "과학 1반");

        // 3. 조회된 클래스의 주인이 진짜 teacher인지 확인
        assertThat(result).extracting("owner.username")
                .containsOnly("teacher1");
    }


    @Test
    @DisplayName("리팩토링 후 Classroom 가져오는 코드 해결")
    void findAllByOwnerOptimization() {
        // given (상황 설정)
        // 1. 교사(User) 데이터 생성 및 저장
        // (User는 추상 클래스이므로 구현체인 Educator를 생성합니다)
        Educator teacher = Educator.educatorBuilder()
                .username("teacher1")
                .password("password123")
                .email("teacher@test.com")
                .role(Role.ROLE_EDUCATOR)
                .gender(Gender.MALE)
                .state(State.ACTIVE)
                .isAuthorized(IsAuthorized.YES)
                .birthday(java.sql.Date.valueOf(LocalDate.now()))
                .build();

        // 2. 비교군이 될 다른 교사 생성 (이 사람 데이터는 조회되면 안 됨)
        Educator otherTeacher = Educator.educatorBuilder()
                .username("other1")
                .password("password123")
                .email("other@test.com")
                .role(Role.ROLE_EDUCATOR)
                .gender(Gender.FEMALE)
                .state(State.ACTIVE)
                .isAuthorized(IsAuthorized.YES)
                .birthday(java.sql.Date.valueOf(LocalDate.now()))
                .build();

        userRepository.saveAll(List.of(teacher, otherTeacher));

        // 3. teacher의 클래스 2개 생성
        ClassroomClass class1 = new ClassroomClass(ClassroomStudentGrade.HI1, ClassroomSubjectType.MATH, ClassroomDataType.SEED,
                "수학 1반", "기초", "설명", teacher, "thumb1.jpg");

        ClassroomClass class2 = new ClassroomClass(ClassroomStudentGrade.HI1, ClassroomSubjectType.EARTH_SCIENCE, ClassroomDataType.SEED,
                "과학 1반", "심화", "설명", teacher, "thumb2.jpg");

        // 4. otherTeacher의 클래스 1개 생성 (노이즈 데이터)
        ClassroomClass otherClass = new ClassroomClass(ClassroomStudentGrade.MD1, ClassroomSubjectType.CHEMISTRY, ClassroomDataType.SEED,
                "영어 1반", "기초", "설명", otherTeacher, "thumb3.jpg");

        classroomClassRepository.saveAll(List.of(class1, class2, otherClass));

        // when (테스트 실행)
        // teacher가 주인이 클래스만 찾아오라고 시킴
        List<ClassroomClass> result = classroomClassRepository.findAllByOwnerOptimization(teacher);

        // then (검증)
        // 1. 개수가 2개여야 함 (otherClass는 제외)
        assertThat(result).hasSize(2);

        // 2. 조회된 클래스의 제목이 내가 넣은 것과 일치하는지 확인
        assertThat(result).extracting("title")
                .containsExactlyInAnyOrder("수학 1반", "과학 1반");

        // 3. 조회된 클래스의 주인이 진짜 teacher인지 확인
        assertThat(result).extracting("owner.username")
                .containsOnly("teacher1");
    }
}