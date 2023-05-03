package com.example.demo.redis.entity;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value = "authNum", timeToLive = 300)
public class AuthNum {
    @Id
    private String email;
    private String authNum;

    private AuthNum() {}

    private AuthNum(String email, String authNum) {
        this.email = email;
        this.authNum = authNum;
    }

    public static AuthNum of(String email, String registerAuthNum) {
        return new AuthNum(email, registerAuthNum);
    }
}
