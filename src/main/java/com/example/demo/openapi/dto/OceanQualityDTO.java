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
