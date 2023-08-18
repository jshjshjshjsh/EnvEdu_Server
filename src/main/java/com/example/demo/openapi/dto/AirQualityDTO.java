package com.example.demo.openapi.dto;

import com.example.demo.openapi.model.parent.AirQualityParent;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AirQualityDTO extends AirQualityParent {

    public List<AirQualityDTO> convertToAirQuality(ResponseEntity<String> response) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();

        JsonNode result = mapper.readTree(response.getBody()).get("response").get("body").get("items");
        List<AirQualityDTO> airKoreaDtos = new ArrayList<>();

        for (Iterator<JsonNode> it = result.elements(); it.hasNext(); ) {
            JsonNode node = it.next();
            airKoreaDtos.add(mapper.treeToValue(node, AirQualityDTO.class));
        }

        return airKoreaDtos;
    }
}
