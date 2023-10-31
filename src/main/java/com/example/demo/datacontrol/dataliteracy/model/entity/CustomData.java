package com.example.demo.datacontrol.dataliteracy.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class CustomData {
    @Id @GeneratedValue
    private Long id;
    private String properties;
    private String data;
    private String memo;
    @Column(columnDefinition = "BINARY(16)")
    private UUID uuid;
    private LocalDateTime saveDate;

    public CustomData(String properties, String data, String memo, UUID uuid, LocalDateTime saveDate) {
        this.properties = properties;
        this.data = data;
        this.memo = memo;
        this.uuid = uuid;
        this.saveDate = saveDate;
    }
}
