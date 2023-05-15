package com.example.demo.openapi.service;

import com.example.demo.openapi.model.AirQualityDTO;
import com.example.demo.openapi.model.OceanQualityDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Iterator;

@Service
public class OpenApiService {
    public ArrayList<AirQualityDTO> convertToAirQuality(ResponseEntity<String> response) throws JsonProcessingException {


        /* ObjectMapping from response */
        ObjectMapper mapper = new ObjectMapper();

        // todo: 여기 재활용할수있을 꺼 같은데
        // note: readTree() 까지를 인터페이스랑 함수로 빼고 그 다음을 구현하는 방식?
        // "response", "body"를 다중인자로 받고 for 문을 돌리고 제네릭쓰면 다 재활용 될듯


        JsonNode result = mapper.readTree(response.getBody()).get("response").get("body").get("items");
        ArrayList<AirQualityDTO> airKoreaDtos = new ArrayList<>();

        for (Iterator<JsonNode> it = result.elements(); it.hasNext(); ) {
            JsonNode node = it.next();
            airKoreaDtos.add(mapper.treeToValue(node, AirQualityDTO.class));
        }

        return airKoreaDtos;
    }
    public ArrayList<OceanQualityDTO> convertToOceanQuality(ResponseEntity<String> response) throws JsonProcessingException {


        /* ObjectMapping from response */
        ObjectMapper mapper = new ObjectMapper();


        JsonNode result = mapper.readTree(response.getBody()).get("getWaterMeasuringListMavg").get("item");
        ArrayList<OceanQualityDTO> oceanQualityDTOS = new ArrayList<>();

        for (Iterator<JsonNode> it = result.elements(); it.hasNext(); ) {
            JsonNode node = it.next();
            oceanQualityDTOS.add(mapper.treeToValue(node, OceanQualityDTO.class).setupStationName());
        }
        System.out.println("oceanQualityDTOS = " + oceanQualityDTOS);

        return oceanQualityDTOS;
    }

    @ExceptionHandler(JsonProcessingException.class)
    private void illegalArgumentExceptionHandler(HttpServletResponse response)
    {
        response.setStatus(HttpStatus.CONFLICT.value());
    }
}
