package com.example.demo.datacontrol.dataliteracy.model.entity;

import com.example.demo.datacontrol.dataliteracy.model.dto.CustomDataDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(value = "customDataRedis", timeToLive = 3600)
public class CustomDataRedis {

    @Id
    private String code;
    private String properties;
    private String data;

    private CustomDataRedis(String code, List<String> properties, List<List<String>> data){
        this.code = code;
        this.properties = properties.toString();
        this.data = data.toString();
    }
    public static CustomDataRedis of(String code, List<String> properties, List<List<String>> data){ return new CustomDataRedis(code, properties, data); };
}
