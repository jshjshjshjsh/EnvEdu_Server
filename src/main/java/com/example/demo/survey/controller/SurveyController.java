package com.example.demo.survey.controller;

import com.example.demo.jwt.util.JwtUtil;
import com.example.demo.survey.domain.dto.SurveyCreateRequestDto;
import com.example.demo.survey.domain.dto.SurveyAnswerRequestDto;
import com.example.demo.survey.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
public class SurveyController {

    private final SurveyService surveyService;
    /**
     * 질문 : 조회(o), 등록(o), 수정
     * 답변 : 조회, 등록(o)
     * 질문 링크 : 조회(o)
     * 상품권 증정 (이미지) : 등록(o), 조회(o)
     * */



    /** INFO : 설계 제약 사항으로 업로드할 때 대상 Survey의 InviteCode를 입력해줘야 함 */
    @RequestMapping(value = "/survey/reward/upload/{inviteCode}", method = RequestMethod.POST, produces = "application/json", consumes = "multipart/form-data")
    public ResponseEntity<?> addSeasonTicketPicture(
            @PathVariable String inviteCode,
            @RequestParam(value = "files") List<MultipartFile> files){

        try {
            return new ResponseEntity<>(surveyService.saveFiles(inviteCode, files), HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("업로드에 실패하였습니다 Error:" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/survey/answer/{inviteCode}")
    public ResponseEntity<?> getSurveyFormat(@PathVariable String inviteCode){

        return new ResponseEntity<>(surveyService.getSurveyEntityAndAttributes(inviteCode), HttpStatus.OK);
    }

    /** INFO : 설문 응답 시 상품권 이미지 반환 */
    @PostMapping("/survey/answer")
    public ResponseEntity<?> answerSurvey(@RequestBody SurveyAnswerRequestDto surveyRequestDto, HttpServletRequest request){
        //Map<String, Object> userInfo = JwtUtil.getJwtRefreshTokenFromCookieAndParse(request.getCookies()).get(JwtUtil.claimName).asMap();
        //return new ResponseEntity<>(surveyService.answerSurvey(surveyRequestDto, userInfo.get(JwtUtil.claimUsername).toString()), HttpStatus.OK);
        return new ResponseEntity<>(surveyService.answerSurvey(surveyRequestDto, "Student1"), HttpStatus.OK);
    }

    @PostMapping("/admin/survey/create")
    public ResponseEntity<?> createSurveyFormat(@RequestBody SurveyCreateRequestDto surveyCreateRequestDto, HttpServletRequest request){
        Map<String, Object> userInfo = JwtUtil.getJwtRefreshTokenFromCookieAndParse(request.getCookies()).get(JwtUtil.claimName).asMap();
        return new ResponseEntity<>(surveyService.createSurveyFormat(surveyCreateRequestDto, userInfo.get(JwtUtil.claimUsername).toString()), HttpStatus.OK);
        //return new ResponseEntity<>(surveyService.createSurveyFormat(surveyCreateRequestDto, "admin"), HttpStatus.OK);
    }

    // todo: 내가 만든 질문 리스트를 가져오는 API
    @GetMapping("/admin/survey/list")
    public ResponseEntity<?> getSurveyList(HttpServletRequest request){
        Map<String, Object> userInfo = JwtUtil.getJwtRefreshTokenFromCookieAndParse(request.getCookies()).get(JwtUtil.claimName).asMap();
        return new ResponseEntity<>(surveyService.getSurveyList(userInfo.get(JwtUtil.claimUsername).toString()), HttpStatus.OK);
        //return new ResponseEntity<>(surveyService.getSurveyList("admin"), HttpStatus.OK);
    }

    @ExceptionHandler(IOException.class)
    private ResponseEntity<?> iOException(IOException e) {
        return new ResponseEntity<>("업로드에 실패하였습니다 Error:" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoSuchElementException.class)
    private ResponseEntity<?> noSuchElementException(NoSuchElementException e) {
        return new ResponseEntity<>("저장된 데이터 값이 없습니다.", HttpStatus.BAD_REQUEST);
    }
}
