package com.example.demo.datacontrol.dataclassroom.controller;

import com.example.demo.datacontrol.dataclassroom.service.ClassroomService;
import com.example.demo.jwt.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ClassroomController {

    private final ClassroomService classroomService;

    @GetMapping("/classroom/list")
    public ResponseEntity<?> getAllClassroomTest(HttpServletRequest request){
        Map<String, Object> userInfo = JwtUtil.getJwtRefreshTokenFromCookieAndParse(request.getCookies()).get(JwtUtil.claimName).asMap();
        return new ResponseEntity<>(classroomService.findAllClassroom(userInfo.get(JwtUtil.claimUsername).toString()), HttpStatus.OK);
        //return new ResponseEntity<>(classroomService.findAllClassroom("Educator1"), HttpStatus.OK);
    }
}
