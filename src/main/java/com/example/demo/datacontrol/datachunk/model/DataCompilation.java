package com.example.demo.datacontrol.datachunk.model;

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
public class DataCompilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JsonIgnore
    private User owner;
    private LocalDateTime saveDate;
    private String dataLabel;
    @Column(columnDefinition = "BINARY(16)")
    private UUID dataUUID;
    private int dataSize;
    private String memo;

    public DataCompilation(User owner, LocalDateTime saveDate, String dataLabel, UUID uuid, int dataSize) {
        this.owner = owner;
        this.saveDate = saveDate;
        this.dataLabel = dataLabel;
        this.dataUUID = uuid;
        this.dataSize = dataSize;
    }
}
