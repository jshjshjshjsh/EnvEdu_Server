package com.example.demo.openapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OceanQualityDTO {
    @JsonProperty("ROWNO")
    private String ROWNO;
    private String stationName;
    @JsonProperty("PTNM")
    private String PTNM;
    @JsonProperty("WMOD")
    private String WMOD;
    @JsonProperty("WMYR")
    private String WMYR;
    @JsonProperty("ITEMTEMP")
    private String ITEMTEMP;
    @JsonProperty("ITEMPH")
    private String ITEMPH;
    @JsonProperty("ITEMDOC")
    private String ITEMDOC;
    @JsonProperty("ITEMBOD")
    private String ITEMBOD;
    @JsonProperty("ITEMCOD")
    private String ITEMCOD;
    @JsonProperty("ITEMSS")
    private String ITEMSS;
    @JsonProperty("ITEMTCOLI")
    private String ITEMTCOLI;
    @JsonProperty("ITEMTN")
    private String ITEMTN;
    @JsonProperty("ITEMTP")
    private String ITEMTP;

    public OceanQualityDTO setupStationName() {
        this.stationName = this.PTNM;
        return this;
    }
}
