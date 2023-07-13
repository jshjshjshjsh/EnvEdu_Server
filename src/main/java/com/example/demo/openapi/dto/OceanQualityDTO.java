package com.example.demo.openapi.dto;

import com.example.demo.openapi.model.parent.OceanQualityParent;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.ToString;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OceanQualityDTO extends OceanQualityParent {

    public static String[] values = {"조사지점명","조사지점명","측정년도","측정월","측정값(수온) (단위 : °C)","측정값(수소이온 농도 (pH))","측정값(용존산소(DO)) (단위 : ㎎/L)","측정값(생물화학 적산소요구량(BOD)) (단위 : ㎎/L)","측정값(화학적산 소요구량(COD)) (단위 : ㎎/L)","측정값(총질소(T-N)) (단위 : ㎎/L)", "측정값(총인(T-P)) (단위 : ㎎/L)", "측정값(투명도) (단위 : ㎎/L)","측정값(클로로필-a(Chlorophyll-a)) (단위 : ㎎/L)", "측정값(전기전도도(EC)) (단위 : μS/㎝)", "측정값(총유기탄소(TOC)) (단위 : ㎎/L)"};
    public OceanQualityDTO setupStation() {
        setupStationName();
        return this;
    }

    public List<OceanQualityDTO> convertToOceanQuality(ResponseEntity<String> response) throws JsonProcessingException {


        /* ObjectMapping from response */
        ObjectMapper mapper = new ObjectMapper();


        JsonNode result = mapper.readTree(response.getBody()).get("getWaterMeasuringListMavg").get("item");
        List<OceanQualityDTO> oceanQualityDTOS = new ArrayList<>();

        for (Iterator<JsonNode> it = result.elements(); it.hasNext(); ) {
            JsonNode node = it.next();
            oceanQualityDTOS.add(mapper.treeToValue(node, OceanQualityDTO.class).setupStation());
        }

        return oceanQualityDTOS;
    }
}
