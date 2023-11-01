package com.example.demo.datacontrol.dataliteracy.model.entity;

import com.example.demo.user.model.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User owner;

    public CustomData(String properties, String data, String memo, UUID uuid, LocalDateTime saveDate, User owner) {
        this.properties = properties;
        this.data = data;
        this.memo = memo;
        this.uuid = uuid;
        this.saveDate = saveDate;
        this.owner = owner;
    }
}
