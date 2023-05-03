package com.example.demo.redis.repo;

import com.example.demo.redis.entity.AuthNum;
import org.springframework.data.repository.CrudRepository;

public interface AuthNumRepository extends CrudRepository<AuthNum, String> {
}
