package com.example.demo.classroom;

import com.example.demo.datacontrol.dataclassroom.domain.types.ClassroomDataType;
import com.example.demo.datacontrol.dataclassroom.domain.types.ClassroomStudentGrade;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class ClassroomTest {

    @Test
    public void classroomEnumTest() {
        List<ClassroomStudentGrade> collect = Stream.of(ClassroomStudentGrade.values()).collect(Collectors.toList());
        System.out.println("grade = " + collect.toString());
    }

    @Test
    public void classroomEnumLabelSelect(){

    }
}
