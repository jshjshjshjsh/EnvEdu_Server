package com.example.demo.user.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class StudentAddDTO {
    private List<String> studentUsernames;

    @Override
    public String toString() {
        return studentUsernames.toString();
    }
}
