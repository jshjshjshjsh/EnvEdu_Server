package com.example.demo.jpa.openapi.dto;

import com.example.demo.jpa.openapi.model.entity.AirQuality;
import lombok.Getter;

import java.util.List;

@Getter
public class AirQualityRequestDto {
    List<AirQuality> data;
    String memo;
}
