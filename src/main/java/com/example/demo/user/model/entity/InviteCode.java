package com.example.demo.user.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class InviteCode {
    @Id @GeneratedValue
    private Long id;
    @ManyToOne
    @JsonIgnore
    private User user;
    private String code;
    private LocalDateTime generateTime;
    private LocalDateTime expireTime;

    public InviteCode(User user, String code, LocalDateTime generateTime, LocalDateTime expireTime) {
        this.user = user;
        this.code = code;
        this.generateTime = generateTime;
        this.expireTime = expireTime;
    }
    public void updateInviteCode(String code, LocalDateTime generateTime, LocalDateTime expireTime){
        this.code = code;
        this.generateTime = generateTime;
        this.expireTime = expireTime;
    }
}
