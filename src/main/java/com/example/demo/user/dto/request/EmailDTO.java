package com.example.demo.user.dto.request;

import lombok.Getter;

import javax.validation.constraints.Pattern;

@Getter
public class EmailDTO {
    @Pattern(regexp = "^[\\da-zA-Z]([-_.]?[\\da-zA-Z])*@[\\da-zA-Z]([-_.]?[\\da-zA-Z])*.[a-zA-Z]{2,3}$", message = "잘못된 형식의 이메일입니다")
    private String email;

    public EmailDTO() {}

    public EmailDTO(String email) {
        this.email = email;
    }
}
