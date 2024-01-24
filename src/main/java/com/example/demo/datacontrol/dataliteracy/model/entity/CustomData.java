package com.example.demo.datacontrol.dataliteracy.model.entity;

import com.example.demo.datacontrol.datachunk.model.parent.Data;
import com.example.demo.datacontrol.datachunk.model.parent.DataEnumTypes;
import com.example.demo.datacontrol.dataliteracy.model.dto.CustomDataListener;
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
@EntityListeners(CustomDataListener.class)
public class CustomData extends Data implements Cloneable {
    @Id @GeneratedValue
    private Long id;
    private String properties;
    private String data;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User owner;
    @Transient
    private String username;
    private Long classId;
    private Long chapterId;
    private Long sequenceId;
    private Boolean isSubmit = false;

    @Override
    public CustomData clone() {
        try {
            return (CustomData) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();  // 예외 발생 시는 일어날 수 없는 상황
        }
    }

    public void updateIsSubmit(Boolean isSubmit) {
        this.isSubmit = isSubmit;
    }
    public void updateOwner(User owner){
        this.owner = owner;
    }
    public void updateUsername(){
        this.username = owner.getUsername();
    }
    public CustomData(String properties, String data, String memo, UUID uuid, LocalDateTime saveDate, User owner,
                      Long classId, Long chapterId, Long sequenceId, Boolean isSubmit) {
        this.properties = properties;
        this.data = data;
        this.owner = owner;
        this.classId = classId;
        this.chapterId = chapterId;
        this.sequenceId = sequenceId;
        this.isSubmit = isSubmit;
        super.updateBasicAttribute(uuid, saveDate, memo, DataEnumTypes.CUSTOM);
    }
}
