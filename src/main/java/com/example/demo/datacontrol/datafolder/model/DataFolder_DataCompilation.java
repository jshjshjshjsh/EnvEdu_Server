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
    @Column(name = "dataFolder_DataCompilation_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "dataFolder_id")
    private DataFolder dataFolder;
    @ManyToOne
    @JoinColumn(name = "seed_id")
    private Seed seed;
    @ManyToOne
    @JoinColumn(name = "airQuality_id")
    private AirQuality airQuality;
    @ManyToOne
    @JoinColumn(name = "oceanQuality_id")
    private OceanQuality oceanQuality;

    public void addDataFolder(DataFolder inputDataFolder) {
        dataFolder = inputDataFolder;
    }
    public void addSeed(Seed inputSeed) {
        seed = inputSeed;
    }

}
