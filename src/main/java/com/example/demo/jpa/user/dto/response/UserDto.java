package com.example.demo.jpa.user.dto.response;

import com.example.demo.jpa.datacontrol.datachunk.model.MeasuredUnit;
import com.example.demo.jpa.user.model.entity.User;
import com.example.demo.jpa.user.model.enumerate.Gender;
import com.example.demo.jpa.user.model.enumerate.Role;
import com.example.demo.jpa.user.model.enumerate.State;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.Date;

@Getter
public class UserDto {
    private final Long id;
    private final String username;
    private final String email;
    private final Date birthday;
    private final Role role;
    private final Gender gender;
    private final State state;
    private final String nickname;
    private final Timestamp updatedTime;
    private final MeasuredUnit measuredUnit;

    public UserDto(User user) {
        id = user.getId();
        username = user.getUsername();
        email = user.getEmail();
        birthday = user.getBirthday();
        role = user.getRole();
        gender = user.getGender();
        state = user.getState();
        updatedTime = user.getUpdatedTime();
        measuredUnit = user.getMeasuredUnit();
        nickname = user.getNickname();
    }
}
