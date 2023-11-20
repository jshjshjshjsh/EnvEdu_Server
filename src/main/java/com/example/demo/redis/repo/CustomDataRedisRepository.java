package com.example.demo.redis.repo;

import com.example.demo.datacontrol.dataliteracy.model.entity.CustomDataRedis;
import org.springframework.data.repository.CrudRepository;

public interface CustomDataRedisRepository extends CrudRepository<CustomDataRedis, String> {
}
