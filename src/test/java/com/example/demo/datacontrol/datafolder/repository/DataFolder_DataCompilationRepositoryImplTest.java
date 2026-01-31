package com.example.demo.datacontrol.datafolder.repository;

import com.example.demo.datacontrol.datafolder.model.DataFolder;
import com.example.demo.datacontrol.datafolder.model.DataFolder_DataCompilation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@DataJpaTest
@Import(DataFolder_DataCompilationRepositoryImpl.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DataFolder_DataCompilationRepositoryImplTest {

    @Autowired
    private DataFolder_DataCompilationRepository dataFolder_dataCompilationRepository;

    @Autowired
    private EntityManager em;

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
    @DisplayName("Folder Id로 조회 테스트")
    void findByDataFolderId() {
        // given
        // 1. 테스트용 DataFolder 생성 및 저장
        DataFolder dataFolder = new DataFolder();
        em.persist(dataFolder);

        // 2. 해당 폴더에 속하는 데이터 컴필레이션 생성 및 저장 (2개)
        DataFolder_DataCompilation comp1 = new DataFolder_DataCompilation();
        comp1.addDataFolder(dataFolder);
        em.persist(comp1);

        DataFolder_DataCompilation comp2 = new DataFolder_DataCompilation();
        comp2.addDataFolder(dataFolder);
        em.persist(comp2);

        // 3. 다른 폴더에 속하는 데이터 (검증 시 조회되면 안 됨)
        DataFolder otherFolder = new DataFolder();
        em.persist(otherFolder);
        DataFolder_DataCompilation otherComp = new DataFolder_DataCompilation();
        otherComp.addDataFolder(otherFolder);
        em.persist(otherComp);

        // 영속성 컨텍스트 초기화 (DB에서 확실히 조회해오도록)
        em.flush();
        em.clear();

        // when
        List<DataFolder_DataCompilation> result = dataFolder_dataCompilationRepository.findByDataFolderId(dataFolder.getId());

        // then
        // 1. 개수 확인 (2개여야 함)
        assertThat(result).hasSize(2);

        // 2. 조회된 데이터들이 요청한 folderId를 가지고 있는지 확인
        assertThat(result).extracting("dataFolder.id")
                .containsOnly(dataFolder.getId());

        // 3. 다른 폴더의 데이터는 포함되지 않았는지 확인 (선택 사항)
        assertThat(result).extracting("id")
                .doesNotContain(otherComp.getId());
    }
}