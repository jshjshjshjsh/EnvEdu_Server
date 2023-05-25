package com.example.demo.user.dto.response;

import com.example.demo.user.model.entity.Educator;
import com.example.demo.user.model.entity.Student;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class Student_EducatorDTO {
    private final List<Student_EducatorDTOElem> elems;

    public Student_EducatorDTO(Educator educator, List<Student> users) {
        elems = users.stream()
                .map(user -> new Student_EducatorDTOElem(educator.getUsername(), user.getUsername()))
                .collect(Collectors.toList());
    }

    @Getter
    public static class Student_EducatorDTOElem {
        private final String educatorUsername;
        private final String studentUsername;

        public Student_EducatorDTOElem(String educatorUsername, String studentUsername) {
            this.educatorUsername = educatorUsername;
            this.studentUsername = studentUsername;
        }
    }
}
