package com.example.demo.openapi.controller;

import com.example.demo.openapi.dto.AirQualityDTO;
import com.example.demo.openapi.dto.OceanQualityDTO;
import com.example.demo.openapi.dto.OpenApiParam;
import com.example.demo.openapi.module.OpenApiRequest;
import com.example.demo.openapi.service.OpenApiService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
public class OpenApiController {

    private final OpenApiService openApiService;

    @GetMapping("/air-quality")
    public ArrayList<AirQualityDTO> getAirQuality(@RequestParam("location") String location) {

        OpenApiRequest apiGet = new OpenApiRequest();
        ResponseEntity<String> response = null;
        try {
            response = apiGet.call(new OpenApiParam.OpenApiParamBuilder()
                    .setDomain("https://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty?")
                    .setKey("serviceKey", "returnType", "numOfRows", "pageNo", "sidoName", "ver")
                    .setValue("e8+m6IjXLkLni3n0tRD3uZP2n99kpV78As6yiB5vAVBZoQUqcuRkDgxqDINHF4dkThE9WmZHNiXs258Egat3Hw==",
                            "json", "100", "1", location, "1.0")
                    .build());

            return openApiService.convertToAirQuality(response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/ocean-quality")
    public ArrayList<OceanQualityDTO> getOceanQuality(@RequestParam(name="year", defaultValue = "2022") String wmyrList, @RequestParam(name="months", defaultValue = "") String months) {
        System.out.println("months = " + months);

        OpenApiRequest apiGet = new OpenApiRequest();
        ResponseEntity<String> response = null;
        try {
            response = apiGet.call(new OpenApiParam.OpenApiParamBuilder()
                    .setDomain("https://apis.data.go.kr/1480523/WaterQualityService/getWaterMeasuringListMavg?")
                    .setKey("ServiceKey", "pageNo", "numOfRows", "resultType", "ptNoList", "wmyrList", "wmodList")
                    .setValue("e8+m6IjXLkLni3n0tRD3uZP2n99kpV78As6yiB5vAVBZoQUqcuRkDgxqDINHF4dkThE9WmZHNiXs258Egat3Hw==",
                            "1", "50", "JSON", "", wmyrList, months)
                    .build());

            return openApiService.convertToOceanQuality(response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @ExceptionHandler(UnsupportedEncodingException.class)
    private void unsupportedEncodingExceptionHandler(HttpServletResponse response)
    {
        response.setStatus(HttpStatus.CONFLICT.value());
    }

    @ExceptionHandler(JsonProcessingException.class)
    private void jsonProcessingExceptionHandler(HttpServletResponse response)
    {
        response.setStatus(HttpStatus.CONFLICT.value());
    }
}
