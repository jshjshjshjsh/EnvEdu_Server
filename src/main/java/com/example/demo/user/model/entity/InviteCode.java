package com.example.demo.user.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Random;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InviteCode {
    @Id @GeneratedValue
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;
    private String code;
    private LocalDateTime generateTime;
    private LocalDateTime expireTime;

    private InviteCode(User user) {
        this.user = user;
        renewCode(); // 코드 생성 로직 공통화
    }

    public static InviteCode generate(User user) {
        return new InviteCode(user);
    }

    public void updateInviteCode(){
        renewCode();
    }

    private void renewCode() {
        LocalDateTime now = LocalDateTime.now();
        this.generateTime = now;
        this.expireTime = now.plusHours(1L);
        this.code = generateRandomCode();
    }

    private String generateRandomCode(){
        String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuilder randomBuilder = new StringBuilder(6);

        for (int i = 0; i < 6; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            randomBuilder.append(randomChar);
        }
        return randomBuilder.toString();
    }
}
