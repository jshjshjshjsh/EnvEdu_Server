package com.example.demo.openapi.repository;

import com.example.demo.openapi.model.entity.AirQuality;
import com.example.demo.openapi.model.entity.OceanQuality;
import com.example.demo.seed.model.Seed;

import java.time.LocalDateTime;
import java.util.List;

public interface OpenApiRepository {
    List<AirQuality> findAirQualityAllByUserId(Long id);
    List<OceanQuality> findOceanQualityAllByUserId(Long id);
    boolean saveAirQuality(List<AirQuality> airQualities);
    boolean saveOceanQuality(List<OceanQuality> oceanQualities);

    // todo: 여기 뜯어 고쳐야됨
    List<AirQuality> findAllByDataTimeBetween(LocalDateTime start, LocalDateTime end);
}
