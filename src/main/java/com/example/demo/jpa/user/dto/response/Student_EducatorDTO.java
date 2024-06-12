package com.example.demo.jpa.user.dto.response;

import com.example.demo.jpa.user.model.entity.Educator;
import com.example.demo.jpa.user.model.entity.Student;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class Student_EducatorDTO {
    private final List<Student_EducatorDTOElem> elems;

    public Student_EducatorDTO(Educator educator, List<Student> users) {
        elems = users.stream()
                .map(user -> new Student_EducatorDTOElem(educator.getUsername(), user.getId(), user.getUsername()))
                .collect(Collectors.toList());
    }

    @Getter
    public static class Student_EducatorDTOElem {
        private final String educatorUsername;
        private final Long id;
        private final String studentUsername;

        public Student_EducatorDTOElem(String educatorUsername, Long id, String studentUsername) {
            this.educatorUsername = educatorUsername;
            this.id = id;
            this.studentUsername = studentUsername;
        }
    }
}
