package com.example.demo.openapi.model.entity;

import com.example.demo.openapi.model.parent.AirQualityParent;
import com.example.demo.user.model.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "AirQuality",
        uniqueConstraints = @UniqueConstraint(columnNames = {"dataTime", "stationName"}))
public class AirQuality extends AirQualityParent {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User owner;
    public void setOwner(User owner) {
        this.owner = owner;
    }

    public AirQuality() {
        super();
    }
    public AirQuality(LocalDateTime dataTime, String stationName, String so2Value, String coValue, String o3Value, String no2Value, String pm10Value, String pm25Value) {
        super(dataTime, stationName, so2Value, coValue, o3Value, no2Value, pm10Value, pm25Value);
    }
}
