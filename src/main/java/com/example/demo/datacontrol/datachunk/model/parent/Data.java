package com.example.demo.datacontrol.datachunk.model.parent;

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
    public void addUuid(UUID uuid){
        this.dataUUID = uuid;
    }
    public void addSaveDate(LocalDateTime saveDate) {
        this.saveDate = saveDate;
    }
}
