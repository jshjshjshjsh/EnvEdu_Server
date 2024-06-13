package com.example.demo.jpa.datacontrol.datachunk.model.parent;

import com.example.demo.jpa.datacontrol.dataliteracy.model.entity.CustomData;
import com.example.demo.jpa.openapi.model.entity.AirQuality;
import com.example.demo.jpa.openapi.model.entity.OceanQuality;
import com.example.demo.jpa.seed.model.Seed;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@ToString
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public class DataSuperTypes {
    @ManyToOne
    private Seed seed;
    @ManyToOne
    private AirQuality airQuality;
    @ManyToOne
    private OceanQuality oceanQuality;
    @ManyToOne
    private CustomData customData;
    private LocalDateTime saveDate;
    public void addSeed(Seed inputSeed) {
        seed = inputSeed;
    }
    public void addAirQuality(AirQuality inputAirQuality) {
        airQuality = inputAirQuality;
    }
    public void addOceanQuality(OceanQuality inputOceanQuality) {
        oceanQuality = inputOceanQuality;
    }
    public void addCustomData(CustomData inputCustomData) {
        customData = inputCustomData;
    }
    public void addSaveDate(LocalDateTime inputSaveDate) {
        saveDate = inputSaveDate;
    }
}
