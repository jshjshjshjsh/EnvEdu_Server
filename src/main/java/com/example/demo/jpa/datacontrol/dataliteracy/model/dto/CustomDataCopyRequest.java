package com.example.demo.jpa.datacontrol.dataliteracy.model.dto;

import com.example.demo.jpa.user.model.entity.Student;
import lombok.Getter;

import java.util.List;

@Getter
public class CustomDataCopyRequest {
    CustomDataDto data;
    List<Student> users;
}
