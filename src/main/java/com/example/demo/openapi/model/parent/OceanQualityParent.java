package com.example.demo.openapi.model.parent;

import com.example.demo.datacontrol.datachunk.model.parent.Data;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class OceanQualityParent extends Data {
    @JsonProperty("PTNM")
    private String ptNm;
    @JsonProperty("WMYR")
    private Integer wmyr;
    @JsonProperty("WMOD")
    private Integer wmod;
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
}
