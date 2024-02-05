package com.example.demo.datacontrol.dataclassroom.controller;

import com.example.demo.datacontrol.dataclassroom.service.ClassroomService;
import com.example.demo.jwt.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ClassroomController {

    private final ClassroomService classroomService;

    @GetMapping("/dataLiteracy/classroom/list")
    public ResponseEntity<?> getAllClassroomTest(@RequestParam(required = false) String grade, @RequestParam(required = false) String subject,
                                                 @RequestParam(required = false) String dataType){
        return new ResponseEntity<>(classroomService.findAllClassroomByGradeSubjectDataType(grade, subject, dataType), HttpStatus.OK);
        //return new ResponseEntity<>(classroomService.findAllClassroomByGradeSubjectDataType(grade, subject, dataType), HttpStatus.OK);
    }

    // todo: Dto 말고 여기서 Types로 검색할 애들 리스트로 반환하는 API 생성

    @GetMapping("/dataLiteracy/classroom/searchTypes")
    public ResponseEntity<?> getSearchTypes(){
        return new ResponseEntity<>(classroomService.getSearchTypes(), HttpStatus.OK);
    }
}
