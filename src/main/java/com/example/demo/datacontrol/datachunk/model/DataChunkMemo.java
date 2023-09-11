package com.example.demo.datacontrol.datachunk.model;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class DataChunkMemo {

    @Id @GeneratedValue
    @Column(name = "dataChunkMemo_id")
    private Long id;
    private String text;
    //@ForeignKey
    private Long chunkId;
}
