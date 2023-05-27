package com.example.demo.user.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class StudentAddDTO {
    public StudentAddDTO() {}

    public StudentAddDTO(List<String> studentUsernames) {
        this.studentUsernames = studentUsernames;
    }
    private List<String> studentUsernames;

    @Override
    public String toString() {
        return studentUsernames.toString();
    }
}
