package com.example.demo.datacontrol.datachart.controller;

import com.example.demo.datacontrol.datachart.domain.entity.CustomDataChart;
import com.example.demo.datacontrol.datachart.service.CustomDataChartService;
import com.example.demo.exceptions.DuplicateAttributeException;
import com.example.demo.jwt.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
public class CustomDataChartController {

    private final CustomDataChartService customDataChartService;

    /**
     * Chart에 대해 저장 / 수정 / 삭제 / 조회(단일 조회, 소속 학생의 차트 전체 조회)
     * */
    /** todo: 1. 차트에 Data 연결 (o)
     *        2. 차트 종류 넣기   (o)
     *        3. 학생들의 차트 전부 조회 (o)
     * */ 

    @PostMapping("/test/dataLiteracy/chart/properties/{username}")
    public ResponseEntity<?> createCustomDataChart(@RequestBody CustomDataChart customDataChart, @PathVariable String username, HttpServletRequest request){
        customDataChartService.createCustomDataChart(customDataChart, username);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/dataLiteracy/chart/properties")
    public ResponseEntity<?> createCustomDataChart(@RequestBody CustomDataChart customDataChart, HttpServletRequest request){
        Map<String, Object> userInfo = JwtUtil.getJwtRefreshTokenFromCookieAndParse(request.getCookies()).get(JwtUtil.claimName).asMap();
        customDataChartService.createCustomDataChart(customDataChart, userInfo.get(JwtUtil.claimUsername).toString());
        //customDataChartService.createCustomDataChart(customDataChart, "Student1");

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/dataLiteracy/chart/students/properties")
    public ResponseEntity<?> getRelatedCustomDataChart(@RequestParam Long classId, @RequestParam Long chapterId,
                                                       @RequestParam Long sequenceId, HttpServletRequest request){
        Map<String, Object> userInfo = JwtUtil.getJwtRefreshTokenFromCookieAndParse(request.getCookies()).get(JwtUtil.claimName).asMap();
        return new ResponseEntity<>(customDataChartService.getRelateCustomDataChart(classId, chapterId, sequenceId, userInfo.get(JwtUtil.claimUsername).toString()), HttpStatus.OK);
        //return new ResponseEntity<>(customDataChartService.getRelateCustomDataChart(classId, chapterId, sequenceId, "Educator1"), HttpStatus.OK);
    }

    @GetMapping("/dataLiteracy/chart/properties/all")
    public ResponseEntity<?> getAllCustomDataChartNotForClass(HttpServletRequest request){
        Map<String, Object> userInfo = JwtUtil.getJwtRefreshTokenFromCookieAndParse(request.getCookies()).get(JwtUtil.claimName).asMap();
        return new ResponseEntity<>(customDataChartService.getAllCustomDataChartNotForClass(userInfo.get(JwtUtil.claimUsername).toString()), HttpStatus.OK);
        //return new ResponseEntity<>(customDataChartService.getAllCustomDataChartNotForClass("Student1"), HttpStatus.OK);
    }


    @GetMapping("/dataLiteracy/chart/properties")
    public ResponseEntity<?> getSingleCustomDataChart(@RequestParam Long classId, @RequestParam Long chapterId,
                                                      @RequestParam Long sequenceId, HttpServletRequest request){
        Map<String, Object> userInfo = JwtUtil.getJwtRefreshTokenFromCookieAndParse(request.getCookies()).get(JwtUtil.claimName).asMap();
        return new ResponseEntity<>(customDataChartService.getSingleCustomDataChart(classId, chapterId, sequenceId, userInfo.get(JwtUtil.claimUsername).toString()), HttpStatus.OK);
        //return new ResponseEntity<>(customDataChartService.getSingleCustomDataChart(classId, chapterId, sequenceId, "Student1"), HttpStatus.OK);
    }

    @ExceptionHandler(NoSuchElementException.class)
    private ResponseEntity<?> noSuchElementException(NoSuchElementException e) {
        return new ResponseEntity<>("저장된 데이터 값이 없습니다.", HttpStatus.BAD_REQUEST);
    }
}
