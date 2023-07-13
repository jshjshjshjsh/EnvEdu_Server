package com.example.demo.openapi.controller;

import com.example.demo.jwt.util.JwtUtil;
import com.example.demo.openapi.dto.AirQualityDTO;
import com.example.demo.openapi.dto.OceanQualityDTO;
import com.example.demo.openapi.model.label.AirQualityLabels;
import com.example.demo.openapi.model.entity.AirQuality;
import com.example.demo.openapi.model.entity.OceanQuality;
import com.example.demo.openapi.model.label.OceanQualityLabels;
import com.example.demo.openapi.service.OpenApiService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
public class OpenApiController {
    @Value("${spring.open-api.gonggong-data}")
    private String serviceKey;
    private final OpenApiService openApiService;

    @GetMapping("/air-quality")
    public ResponseEntity<List<ResponseEntity<?>>>  getAirQuality(@RequestParam(name="location", defaultValue = "부산") String location) throws UnsupportedEncodingException, JsonProcessingException {

        String[] key = {"serviceKey", "returnType", "numOfRows", "pageNo", "sidoName", "ver"};
        String[] value = {serviceKey, "json", "100", "1", location, "1.0"};

        AirQualityDTO airQualityDTO = new AirQualityDTO();

        AirQualityLabels airQualityLabels = new AirQualityLabels();
        List<ResponseEntity<?>> responseEntities = new ArrayList<>();

        ResponseEntity<?> labels = ResponseEntity.ok().body(airQualityLabels.getLabels());
        ResponseEntity<?> datas = ResponseEntity.ok().body(airQualityDTO.convertToAirQuality(openApiService.callApi("https://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty?", key, value)));

        responseEntities.add(labels);
        responseEntities.add(datas);

        return ResponseEntity.ok().body(responseEntities);
        //return new ResponseEntity<>(airQualityDTOS, HttpStatus.OK);
    }

    @GetMapping("/ocean-quality")
    public ResponseEntity<?> getOceanQuality(@RequestParam(name="year", defaultValue = "2022") String wmyrList, @RequestParam(name="months", defaultValue = "06") String months) throws UnsupportedEncodingException, JsonProcessingException {
        String[] key = {"ServiceKey", "pageNo", "numOfRows", "resultType", "ptNoList", "wmyrList", "wmodList"};
        String[] value = {serviceKey, "1", "50", "JSON", "", wmyrList, months};

        ResponseEntity<String> stringResponseEntity = openApiService.callApi("https://apis.data.go.kr/1480523/WaterQualityService/getWaterMeasuringListMavg?", key, value);
        OceanQualityDTO oceanQualityDTO = new OceanQualityDTO();

        OceanQualityLabels oceanQualityLabels = new OceanQualityLabels();
        List<ResponseEntity<?>> responseEntities = new ArrayList<>();

        ResponseEntity<?> labels = ResponseEntity.ok().body(oceanQualityLabels.getLabels());
        ResponseEntity<?> datas = ResponseEntity.ok().body(oceanQualityDTO.convertToOceanQuality(stringResponseEntity));

        responseEntities.add(labels);
        responseEntities.add(datas);

        return ResponseEntity.ok().body(responseEntities);

        //return new ResponseEntity<>(oceanQualityDTO.convertToOceanQuality(stringResponseEntity), HttpStatus.OK);
    }

    @PostMapping("/air-quality")
    public ResponseEntity<?> setAirQuality(@RequestBody List<AirQuality> airQualities, HttpServletRequest request){
        Map<String, Object> userInfo = JwtUtil.getJwtRefreshTokenFromCookieAndParse(request.getCookies()).get(JwtUtil.claimName).asMap();

        openApiService.saveAirQuality(airQualities, userInfo.get(JwtUtil.claimUsername).toString());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/ocean-quality")
    public ResponseEntity<?> setOceanQuality(@RequestBody List<OceanQuality> oceanQualities, HttpServletRequest request){
        Map<String, Object> userInfo = JwtUtil.getJwtRefreshTokenFromCookieAndParse(request.getCookies()).get(JwtUtil.claimName).asMap();

        openApiService.saveOceanQuality(oceanQualities, userInfo.get(JwtUtil.claimUsername).toString());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/air-quality/mine")
    public ResponseEntity<?> getMyAirQuality(@RequestParam String username){
        return new ResponseEntity<>(openApiService.findMyAirQuality(username), HttpStatus.OK);
    }

    @GetMapping("/ocean-quality/mine")
    public ResponseEntity<?> getMyOceanQuality(@RequestParam String username){
        return new ResponseEntity<>(openApiService.findMyOceanQuality(username), HttpStatus.OK);
    }

    @ExceptionHandler(UnsupportedEncodingException.class)
    private ResponseEntity<?> unsupportedEncodingExceptionHandler(UnsupportedEncodingException e)
    {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JsonProcessingException.class)
    private ResponseEntity<?> jsonProcessingExceptionHandler(JsonProcessingException e)
    {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    private ResponseEntity<?> illegalArgumentExceptionHandler(IllegalArgumentException e)
    {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NoSuchElementException.class)
    private ResponseEntity<?> noSuchElementExceptionHandler(NoSuchElementException e)
    {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
