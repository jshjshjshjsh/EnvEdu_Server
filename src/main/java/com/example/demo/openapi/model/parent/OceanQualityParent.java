package com.example.demo.openapi.model.parent;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.MappedSuperclass;

@Getter
@Setter
@ToString
@MappedSuperclass
public abstract class OceanQualityParent {
    @JsonProperty("PTNM")
    private String ptNm;
    private String stationName;
    @JsonProperty("WMYR")
    private String wmyr;
    @JsonProperty("WMOD")
    private String wmod;
    @JsonProperty("ITEMTEMP")
    private String itemTemp;
    @JsonProperty("ITEMPH")
    private String itemPh;
    @JsonProperty("ITEMDOC")
    private String itemDoc;
    @JsonProperty("ITEMBOD")
    private String itemBod;
    @JsonProperty("ITEMCOD")
    private String itemCod;
    @JsonProperty("ITEMTN")
    private String itemTn;
    @JsonProperty("ITEMTP")
    private String itemTp;
    @JsonProperty("ITEMTRANS")
    private String itemTrans;
    @JsonProperty("ITEMCLOA")
    private String itemCloa;
    @JsonProperty("ITEMEC")
    private String itemEc;
    @JsonProperty("ITEMTOC")
    private String itemToc;

    public void setupStationName() {
        this.stationName = this.ptNm;
    }
}
