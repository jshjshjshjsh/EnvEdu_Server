package com.example.demo.datacontrol.dataliteracy.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class DataLiteracyDataset {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    @Column(columnDefinition = "BINARY(16)")
    private UUID uuid;

    public DataLiteracyDataset(String title, String content, UUID uuid) {
        this.title = title;
        this.content = content;
        this.uuid = uuid;
    }
}
