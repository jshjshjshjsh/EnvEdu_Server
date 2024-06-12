package com.example.demo.jpa.datacontrol.dataclassroom.domain.dto;

import com.example.demo.jpa.datacontrol.dataclassroom.domain.types.ClassroomDataType;
import com.example.demo.jpa.datacontrol.dataclassroom.domain.types.ClassroomStudentGrade;
import com.example.demo.jpa.datacontrol.dataclassroom.domain.types.ClassroomSubjectType;
import lombok.Getter;

import java.util.List;

@Getter
public class ClassroomSearchTypeProvideDto {
    private final List<String> grade = ClassroomStudentGrade.getAllLabels();
    private final List<String> subject = ClassroomSubjectType.getAllLabels();
    private final List<String> dataType = ClassroomDataType.getAllLabels();
}
