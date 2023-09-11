package com.example.demo.datacontrol.datafolder.model;

import com.example.demo.openapi.model.entity.AirQuality;
import com.example.demo.openapi.model.entity.OceanQuality;
import com.example.demo.seed.model.Seed;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class DataFolder_DataCompilation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private DataFolder dataFolder;
    @ManyToOne
    private Seed seed;
    @ManyToOne
    private AirQuality airQuality;
    @ManyToOne
    private OceanQuality oceanQuality;

    public void addDataFolder(DataFolder inputDataFolder) {
        dataFolder = inputDataFolder;
    }
    public void addSeed(Seed inputSeed) {
        seed = inputSeed;
    }

}
