package com.example.demo.openapi.dto;

import com.example.demo.openapi.model.parent.AirQualityParent;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AirQualityDTO extends AirQualityParent {

}
