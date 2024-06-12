package com.example.demo.jpa.datacontrol.dataclassroom.controller;

import com.example.demo.jpa.datacontrol.dataclassroom.domain.entity.answer.ClassroomAnswerDataType;
import com.example.demo.jpa.datacontrol.dataclassroom.domain.entity.answer.ClassroomAnswerTextData;
import com.example.demo.jpa.datacontrol.dataclassroom.service.ClassroomAnswerService;
import com.example.demo.jpa.jwt.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ClassroomAnswerController {

    private final ClassroomAnswerService classroomAnswerService;

    @GetMapping("/dataLiteracy/classroom/answer/share")
    public ResponseEntity<?> findSharedTextData(@RequestParam Long classId, @RequestParam Long chapterId, @RequestParam Long sequenceId,
                                                   @RequestParam(required = false) ClassroomAnswerDataType answerType, HttpServletRequest request){
        Map<String, Object> userInfo = JwtUtil.getJwtRefreshTokenFromCookieAndParse(request.getCookies()).get(JwtUtil.claimName).asMap();
        return new ResponseEntity<>(classroomAnswerService.findSharedTextData(userInfo.get(JwtUtil.claimUsername).toString(), classId, chapterId, sequenceId, answerType), HttpStatus.OK);
        //return new ResponseEntity<>(classroomAnswerService.findSharedTextData("Student1", classId, chapterId, sequenceId, answerType), HttpStatus.OK);
    }

    @GetMapping("/dataLiteracy/classroom/answer/submit")
    public ResponseEntity<?> findSubmittedTextData(@RequestParam Long classId, @RequestParam Long chapterId, @RequestParam Long sequenceId,
                                                   @RequestParam(required = false) ClassroomAnswerDataType answerType, HttpServletRequest request){
        Map<String, Object> userInfo = JwtUtil.getJwtRefreshTokenFromCookieAndParse(request.getCookies()).get(JwtUtil.claimName).asMap();
        return new ResponseEntity<>(classroomAnswerService.findSubmittedTextData(userInfo.get(JwtUtil.claimUsername).toString(), classId, chapterId, sequenceId, answerType), HttpStatus.OK);
        //return new ResponseEntity<>(classroomAnswerService.findSubmittedTextData("Educator1", classId, chapterId, sequenceId, answerType), HttpStatus.OK);
    }

    @PutMapping("/dataLiteracy/classroom/answer/shareAndSubmit")
    public ResponseEntity<?> updateShareAndSubmit(@RequestBody ClassroomAnswerTextData textData, HttpServletRequest request) {
        Map<String, Object> userInfo = JwtUtil.getJwtRefreshTokenFromCookieAndParse(request.getCookies()).get(JwtUtil.claimName).asMap();
        classroomAnswerService.updateClassroomTextDataSubmitAndShare(userInfo.get(JwtUtil.claimUsername).toString(), textData);
        //classroomAnswerService.updateClassroomTextDataSubmitAndShare("Student1", textData);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/dataLiteracy/classroom/answer")
    public ResponseEntity<?> deleteTextData(@RequestBody ClassroomAnswerTextData textData, HttpServletRequest request){
        Map<String, Object> userInfo = JwtUtil.getJwtRefreshTokenFromCookieAndParse(request.getCookies()).get(JwtUtil.claimName).asMap();
        classroomAnswerService.deleteClassroomTextData(userInfo.get(JwtUtil.claimUsername).toString(), textData);
        //classroomAnswerService.deleteClassroomTextData("Student1", textData);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/dataLiteracy/classroom/answer")
    public ResponseEntity<?> createTextData(@RequestBody ClassroomAnswerTextData textData, HttpServletRequest request){
        Map<String, Object> userInfo = JwtUtil.getJwtRefreshTokenFromCookieAndParse(request.getCookies()).get(JwtUtil.claimName).asMap();
        classroomAnswerService.createAnswerTextData(userInfo.get(JwtUtil.claimUsername).toString(), textData);
        //classroomAnswerService.createAnswerTextData("Student1", textData);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
