package com.example.demo.jpa.datacontrol.datachunk.model.parent;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@MappedSuperclass
public abstract class Data {
    @Column(columnDefinition = "BINARY(16)")
    private UUID dataUUID;
    private LocalDateTime saveDate;
    private String memo;
    private DataEnumTypes dataLabel;
    public void addUuid(UUID uuid){
        this.dataUUID = uuid;
    }
    public void addSaveDate(LocalDateTime saveDate) {
        this.saveDate = saveDate;
    }

    public void updateBasicAttribute(UUID uuid, LocalDateTime saveDate, String memo, DataEnumTypes dataLabel) {
        this.dataUUID = uuid;
        this.saveDate = saveDate;
        this.memo = memo;
        this.dataLabel = dataLabel;
    }
}
