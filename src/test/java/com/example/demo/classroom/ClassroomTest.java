package com.example.demo.classroom;

import com.example.demo.datacontrol.dataclassroom.domain.entity.Classroom;
import com.example.demo.datacontrol.dataclassroom.repository.ClassroomClassRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@SpringBootTest
public class ClassroomTest {

    @Autowired
    private ClassroomClassRepository classroomRepository;

    @Test
    @Transactional
    //@Rollback(value = false)
    void ClassroomFetchTest(){
        List<Classroom> all = classroomRepository.findAll();
        for (Classroom a :
                all) {
            System.out.println("a = " + a);
        }
    }
}
