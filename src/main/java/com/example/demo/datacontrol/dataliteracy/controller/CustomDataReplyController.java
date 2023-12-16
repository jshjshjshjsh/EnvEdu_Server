package com.example.demo.datacontrol.dataliteracy.controller;

import com.example.demo.datacontrol.dataliteracy.model.dto.CustomDataReplyDto;
import com.example.demo.datacontrol.dataliteracy.model.entity.CustomDataReply;
import com.example.demo.datacontrol.dataliteracy.service.DataLiteracyReplyService;
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
public class CustomDataReplyController {

    private final DataLiteracyReplyService dataLiteracyReplyService;

    @GetMapping("/dataLiteracy/sequenceData/reply")
    public ResponseEntity<?> getCustomDataReply(@RequestParam Long classId, @RequestParam Long chapterId,
                                                @RequestParam Long sequenceId, HttpServletRequest request){
        Map<String, Object> userInfo = JwtUtil.getJwtRefreshTokenFromCookieAndParse(request.getCookies()).get(JwtUtil.claimName).asMap();
        return new ResponseEntity<>(dataLiteracyReplyService.getCustomDataReply(classId, chapterId, sequenceId, userInfo.get(JwtUtil.claimUsername).toString()), HttpStatus.OK);
        //return new ResponseEntity<>(dataLiteracyReplyService.getCustomDataReply(classId, chapterId, sequenceId, "Student1"), HttpStatus.OK);
    }

    @DeleteMapping("/dataLiteracy/sequenceData/reply")
    public ResponseEntity<?> deleteCustomDataReply(@RequestBody CustomDataReply customDataReply, HttpServletRequest request){

        Map<String, Object> userInfo = JwtUtil.getJwtRefreshTokenFromCookieAndParse(request.getCookies()).get(JwtUtil.claimName).asMap();
        HttpStatus responseStatus = HttpStatus.BAD_REQUEST;
        if (dataLiteracyReplyService.deleteCustomDataReply(customDataReply, userInfo.get(JwtUtil.claimUsername).toString()))
        //if (dataLiteracyReplyService.deleteCustomDataReply(customDataReply, "Student1"))
            responseStatus = HttpStatus.OK;

        return new ResponseEntity<>(responseStatus);
    }

    @PutMapping("/dataLiteracy/sequenceData/reply")
    public ResponseEntity<?> updateCustomDataReply(@RequestBody CustomDataReply customDataReply, HttpServletRequest request){

        Map<String, Object> userInfo = JwtUtil.getJwtRefreshTokenFromCookieAndParse(request.getCookies()).get(JwtUtil.claimName).asMap();
        dataLiteracyReplyService.updateCustomDataReply(customDataReply, userInfo.get(JwtUtil.claimUsername).toString());
        //dataLiteracyReplyService.updateCustomDataReply(customDataReply, "Student1");

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/dataLiteracy/sequenceData/reply")
    public ResponseEntity<?> createCustomDataReply(@RequestBody CustomDataReplyDto customDataReplyDto, HttpServletRequest request){

        Map<String, Object> userInfo = JwtUtil.getJwtRefreshTokenFromCookieAndParse(request.getCookies()).get(JwtUtil.claimName).asMap();
        dataLiteracyReplyService.createCustomDataReply(customDataReplyDto, userInfo.get(JwtUtil.claimUsername).toString());
        //dataLiteracyReplyService.createCustomDataReply(customDataReplyDto, "Student1");

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler(NoSuchElementException.class)
    private ResponseEntity<?> noSuchElementException(NoSuchElementException e) {
        return new ResponseEntity<>("저장된 데이터 값이 없습니다.", HttpStatus.BAD_REQUEST);
    }
}
