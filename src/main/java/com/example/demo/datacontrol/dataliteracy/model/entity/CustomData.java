package com.example.demo.datacontrol.dataliteracy.model.entity;

import com.example.demo.datacontrol.datachart.domain.types.AxisType;
import com.example.demo.datacontrol.datachunk.model.parent.Data;
import com.example.demo.datacontrol.datachunk.model.parent.DataEnumTypes;
import com.example.demo.datacontrol.dataliteracy.model.dto.CustomDataListener;
import com.example.demo.user.model.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(CustomDataListener.class)
public class CustomData extends Data implements Cloneable {
    @Id @GeneratedValue
    private Long id;
    @Lob
    private String properties;
    @Lob
    private String data;
    private String axisTypes;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User owner;
    @Transient
    private String username;
    private Long classId;
    private Long chapterId;
    private Long sequenceId;
    private Boolean isSubmit = false;
    private Boolean canSubmit = false;
    private Boolean canShared = false;

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
    public CustomData(String properties, String data, String axisTypes, String memo, UUID uuid, LocalDateTime saveDate, User owner,
                      Long classId, Long chapterId, Long sequenceId, Boolean isSubmit, Boolean canShared, Boolean canSubmit) {
        this.properties = properties;
        this.data = data;
        this.axisTypes = axisTypes;
        this.owner = owner;
        this.classId = classId;
        this.chapterId = chapterId;
        this.sequenceId = sequenceId;
        this.isSubmit = isSubmit;
        this.canShared = canShared;
        this.canSubmit = canSubmit;
        super.updateBasicAttribute(uuid, saveDate, memo, DataEnumTypes.CUSTOM);
    }
}
