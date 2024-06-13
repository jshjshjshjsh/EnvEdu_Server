package com.example.demo.openapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AirQualityStationDTO {
    @JsonProperty("dmX")
    private String dmX;
    @JsonProperty("item")
    private String item;
    @JsonProperty("mangName")
    private String mangName;
    @JsonProperty("year")
    private String year;
    @JsonProperty("stationName")
    private String stationName;
    @JsonProperty("addr")
    private String addr;
    @JsonProperty("dmY")
    private String dmY;

    public List<AirQualityStationDTO> convertToAirQualityStation(ResponseEntity<String> response) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();

        JsonNode result = mapper.readTree(response.getBody()).get("response").get("body").get("items");
        List<AirQualityStationDTO> airKoreaDtos = new ArrayList<>();

        for (Iterator<JsonNode> it = result.elements(); it.hasNext(); ) {
            JsonNode node = it.next();
            airKoreaDtos.add(mapper.treeToValue(node, AirQualityStationDTO.class));
        }

        return airKoreaDtos;
    }
}
