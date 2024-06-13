package com.example.demo.datacontrol.datafolder.dto;

import com.example.demo.datacontrol.datachunk.model.parent.DataSuperTypes;
import com.example.demo.datacontrol.dataliteracy.model.entity.CustomData;
import com.example.demo.openapi.model.entity.AirQuality;
import com.example.demo.openapi.model.entity.OceanQuality;
import com.example.demo.seed.model.Seed;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class DataItems extends DataSuperTypes {
    private Long id;

    public void addId(Long id){
        this.id = id;
    }

    public DataItems(Seed seed, AirQuality airQuality, OceanQuality oceanQuality, CustomData customData, LocalDateTime saveDate, Long id) {
        super(seed, airQuality, oceanQuality, customData, saveDate);
        this.id = id;
    }
}
