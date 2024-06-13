package com.example.demo.jpa.redis.repo;

import com.example.demo.jpa.datacontrol.dataliteracy.model.entity.CustomDataRedis;
import org.springframework.data.repository.CrudRepository;

public interface CustomDataRedisRepository extends CrudRepository<CustomDataRedis, String> {
}
