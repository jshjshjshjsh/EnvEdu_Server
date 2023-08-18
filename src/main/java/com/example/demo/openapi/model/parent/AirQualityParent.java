package com.example.demo.openapi.model.parent;

import lombok.Getter;

import javax.persistence.MappedSuperclass;

@Getter
@MappedSuperclass
public abstract class AirQualityParent {
    private String dataTime;
    private String stationName;
    private String so2Value;
    private String coValue;
    private String o3Value;
    private String no2Value;
    private String pm10Value;
    private String pm25Value;

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }
}
