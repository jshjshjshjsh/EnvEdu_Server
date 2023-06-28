package com.example.demo.openapi.model.parent;

import lombok.Getter;

import javax.persistence.MappedSuperclass;

@Getter
@MappedSuperclass
public abstract class AirQualityParent {
    private String stationName;
    // 미세먼지(PM10) 농도
    private String pm10Value;
    // 미세먼지(PM10) 24시간 예측 이동 농도
    private String pm10Value24;
    // 미세먼지(PM2.5) 농도
    private String pm25Value;
    // 미세먼지(PM2.5) 24시간 예측 이동 농도
    private String pm25Value24;
    // 통합대기환경수치
    private String khaiValue;
    // 통합대기환경지수
    private String khaiGrade;
}
