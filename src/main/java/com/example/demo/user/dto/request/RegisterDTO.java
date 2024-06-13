package com.example.demo.user.dto.request;

import com.example.demo.user.model.enumerate.Gender;
import com.example.demo.user.model.enumerate.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import java.sql.Date;

@Getter
@Setter
public class RegisterDTO {
    @Pattern(regexp = "^(?=.*[a-zA-Z])[a-zA-Z0-9]{5,20}$", message = "잘못된 형식의 아이디입니다")
    private String username;

    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%])[a-zA-Z\\d!@#$%]{8,20}$", message = "잘못된 형식의 비밀번호입니다")
    private String password;

    @Pattern(regexp = "^[\\da-zA-Z]([-_.]?[\\da-zA-Z])*@[\\da-zA-Z]([-_.]?[\\da-zA-Z])*.[a-zA-Z]{2,3}$", message = "잘못된 형식의 이메일입니다")
    private String email;

    private Gender gender;

    private Role role;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    private String nickname;

    public RegisterDTO() {}

    public RegisterDTO(String username, String password, String email, Gender gender, Role role, Date birthday, String nickname) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.gender = gender;
        this.role = role;
        this.birthday = birthday;
        this.nickname = nickname;
    }
}
