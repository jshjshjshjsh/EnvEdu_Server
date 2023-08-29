package com.example.demo.openapi.controller;

import com.example.demo.jwt.util.JwtUtil;
import com.example.demo.openapi.dto.AirQualityDTO;
import com.example.demo.openapi.dto.AirQualityStationDTO;
import com.example.demo.openapi.dto.OceanQualityDTO;
import com.example.demo.openapi.model.entity.AirQuality;
import com.example.demo.openapi.model.entity.OceanQuality;
import com.example.demo.openapi.service.OpenApiService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
public class OpenApiController {
    @Value("${spring.open-api.gonggong-data.air-status}")
    private String serviceKeyAirStatus;
    @Value("${spring.open-api.gonggong-data.air-station}")
    private String serviceKeyAirStation;
    private final OpenApiService openApiService;

    @GetMapping("/air-quality/station")
    public ResponseEntity<?> getAirQualityStation(@RequestParam(name = "addr", defaultValue = "부산") String addr) throws UnsupportedEncodingException, JsonProcessingException {
        String[] key = {"serviceKey", "returnType", "numOfRows", "pageNo", "addr"};
        String[] value = {serviceKeyAirStation, "json", "100", "1", addr};

        AirQualityStationDTO airQualityStationDTO = new AirQualityStationDTO();
        List<AirQualityStationDTO> airQualityStationDTOS = airQualityStationDTO.convertToAirQualityStation(openApiService.callApi("https://apis.data.go.kr/B552584/MsrstnInfoInqireSvc/getMsrstnList?", key, value));

        return new ResponseEntity<>(airQualityStationDTOS, HttpStatus.OK);
    }

    @GetMapping("/air-quality")
    public ResponseEntity<?>  getAirQuality(@RequestParam(name="location", defaultValue = "부산") String location,
                                                                    @RequestParam(name = "stationName", defaultValue = "") String stationName,
                                                                     @RequestParam(name = "dataTerm", defaultValue = "DAILY") String dataTerm) throws UnsupportedEncodingException, JsonProcessingException {

        String[] key = {"serviceKey", "returnType", "numOfRows", "pageNo", "sidoName", "ver"};
        String[] value = {serviceKeyAirStatus, "json", "100", "1", location, "1.0"};
        String url = "https://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty?";

        if(!stationName.isEmpty()){
            key = new String[]{"serviceKey", "returnType", "numOfRows", "pageNo", "stationName", "dataTerm", "ver"};
            value = new String[]{serviceKeyAirStatus, "json", "100", "1", stationName, dataTerm, "1.0"};
            url = "https://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty?";
        }

        AirQualityDTO airQualityDTO = new AirQualityDTO();


        List<AirQualityDTO> airQualityDTOS = airQualityDTO.convertToAirQuality(openApiService.callApi(url, key, value));

        if(!stationName.isEmpty()){
            for(AirQualityDTO single: airQualityDTOS) {
                single.setStationName(stationName);
            }
        }

        return new ResponseEntity<>(airQualityDTOS, HttpStatus.OK);
    }

    @GetMapping("/ocean-quality")
    public ResponseEntity<?> getOceanQuality(@RequestParam(name="year", defaultValue = "2022") String wmyrList, @RequestParam(name="months", defaultValue = "06") String months) throws UnsupportedEncodingException, JsonProcessingException {
        String[] key = {"ServiceKey", "pageNo", "numOfRows", "resultType", "ptNoList", "wmyrList", "wmodList"};
        String[] value = {serviceKeyAirStatus, "1", "50", "JSON", "", wmyrList, months};

        ResponseEntity<String> stringResponseEntity = openApiService.callApi("https://apis.data.go.kr/1480523/WaterQualityService/getWaterMeasuringListMavg?", key, value);
        OceanQualityDTO oceanQualityDTO = new OceanQualityDTO();

        return new ResponseEntity<>(oceanQualityDTO.convertToOceanQuality(stringResponseEntity), HttpStatus.OK);
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

    @DeleteMapping("/air-quality/mine/{airQualityId}")
    public ResponseEntity<?> deleteAirQuality(@PathVariable long airQualityId){


        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/ocean-quality/mine/{oceanQualityId}")
    public ResponseEntity<?> deleteOceanQuality(@PathVariable long oceanQualityId){


        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 연.월.일 시간:분
    @GetMapping("/air-quality/mine")
    public ResponseEntity<?> getMyAirQuality(@RequestParam String username,
                                             @RequestParam(name="startDateTime", defaultValue = "") String startDateTime,
                                             @RequestParam(name="endDateTime", defaultValue = "") String endDateTime){
        LocalDateTime defaultStart = LocalDateTime.of(1900, Month.JANUARY, 1, 0, 0, 0);
        LocalDateTime defaultEnd = LocalDateTime.now();

        if (!startDateTime.isEmpty())
            defaultStart = LocalDateTime.parse(startDateTime, DateTimeFormatter.RFC_1123_DATE_TIME);
        if (!endDateTime.isEmpty())
            defaultEnd = LocalDateTime.parse(endDateTime, DateTimeFormatter.RFC_1123_DATE_TIME);

        return new ResponseEntity<>(openApiService.findMyAirQuality(username, defaultStart, defaultEnd), HttpStatus.OK);
    }

    // 연+월
    @GetMapping("/ocean-quality/mine")
    public ResponseEntity<?> getMyOceanQuality(@RequestParam String username,
                                               @RequestParam(name="startYear", defaultValue = "1900") String startYear,
                                               @RequestParam(name="startMonth", defaultValue = "01") String startMonth,
                                               @RequestParam(name="endYear", defaultValue = "") String endYear,
                                               @RequestParam(name="endMonth", defaultValue = "") String endMonth){
        YearMonth now = YearMonth.now();
        if (endYear.isEmpty())
            endYear = String.valueOf(now.getYear());
        if (endMonth.isEmpty())
            endMonth = String.valueOf(now.getMonthValue());

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
