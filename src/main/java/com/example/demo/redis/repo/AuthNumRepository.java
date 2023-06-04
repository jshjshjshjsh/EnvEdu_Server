package com.example.demo.redis.repo;

import com.example.demo.redis.entity.AuthNum;
import org.springframework.data.repository.CrudRepository;

/**
 * 회원가입시 이메일 인증에 사용되는 인증번호를 위한 레포지토리
 */
public interface AuthNumRepository extends CrudRepository<AuthNum, String> {
}
