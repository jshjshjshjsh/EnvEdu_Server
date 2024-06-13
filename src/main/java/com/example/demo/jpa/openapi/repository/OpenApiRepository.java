package com.example.demo.jpa.openapi.repository;

import com.example.demo.jpa.openapi.model.entity.AirQuality;
import com.example.demo.jpa.openapi.model.entity.OceanQuality;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface OpenApiRepository {
    AirQuality findAirQualityById(Long id);
    OceanQuality findOceanQualityById(Long id);
    List<AirQuality> findAirQualityAllByUserIdAndDataUuid(UUID uuid, Long id);
    List<AirQuality> findAirQualityAllByUserId(Long id);
    List<OceanQuality> findOceanQualityAllByUserIdAndDataUuid(UUID uuid, Long id);
    List<OceanQuality> findOceanQualityAllByUserId(Long id);
    boolean saveAirQuality(List<AirQuality> airQualities);
    boolean saveOceanQuality(List<OceanQuality> oceanQualities);

    // todo: 여기 뜯어 고쳐야됨
    List<AirQuality> findAllByDataTimeBetween(LocalDateTime start, LocalDateTime end);

}
