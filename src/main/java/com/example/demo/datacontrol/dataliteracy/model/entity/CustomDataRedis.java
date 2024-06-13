package com.example.demo.datacontrol.dataliteracy.model.entity;

import com.example.demo.datacontrol.dataliteracy.model.dto.CustomDataDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;

@Getter
@NoArgsConstructor
@RedisHash(value = "customDataRedis", timeToLive = 3600)
public class CustomDataRedis {

    @Id
    private String code;
    private String properties;
    private String axisTypes;
    private String data;
    private String memo;
    private Boolean isSubmit;

    private CustomDataRedis(String code, List<String> properties, List<List<String>> data, List<String> axisTypes, String memo, Boolean isSubmit){
        this.code = code;
        this.properties = properties.toString();
        this.data = data.toString();
        this.axisTypes = axisTypes.toString();
        this.memo = memo;
        this.isSubmit = isSubmit;
    }
    public static CustomDataRedis of(String code, List<String> properties, List<List<String>> data, List<String> axisTypes, String memo, Boolean isSubmit){ return new CustomDataRedis(code, properties, data, axisTypes, memo, isSubmit); };
}
