package com.example.demo.openapi.model.parent;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public abstract class OceanQualityParent {
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

    public void setupStationName() {
        this.stationName = this.PTNM;
    }
}
