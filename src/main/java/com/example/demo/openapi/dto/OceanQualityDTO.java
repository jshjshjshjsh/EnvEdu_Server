package com.example.demo.openapi.dto;

import com.example.demo.openapi.model.parent.OceanQualityParent;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OceanQualityDTO extends OceanQualityParent {

    public OceanQualityDTO setupStation() {
        setupStationName();
        return this;
    }
}
