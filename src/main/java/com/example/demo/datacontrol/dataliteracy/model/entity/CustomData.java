package com.example.demo.datacontrol.dataliteracy.model.entity;

import com.example.demo.user.model.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class CustomData implements Cloneable{
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

    @Override
    public CustomData clone() {
        try {
            return (CustomData) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();  // 예외 발생 시는 일어날 수 없는 상황
        }
    }

    public void updateOwner(User owner){
        this.owner = owner;
    }
    public void updateUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public CustomData(String properties, String data, String memo, UUID uuid, LocalDateTime saveDate, User owner) {
        this.properties = properties;
        this.data = data;
        this.memo = memo;
        this.uuid = uuid;
        this.saveDate = saveDate;
        this.owner = owner;
    }
}
