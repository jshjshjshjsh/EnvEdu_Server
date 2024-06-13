package com.example.demo.user.repository;

import com.example.demo.jpa.redis.entity.AuthNum;
import com.example.demo.jpa.redis.repo.AuthNumRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class RedisTest {
    @Autowired
    private AuthNumRepository authNumRepository;

    @Test
    @Transactional
    void redisInsertTest() {
        AuthNum authNum = AuthNum.of("meme@naver.com", "1234");
        authNumRepository.save(authNum);
        AuthNum findAuthNum = authNumRepository.findById("meme@naver.com").orElse(null);
        Assertions.assertNotEquals(null, findAuthNum);
    }
}
