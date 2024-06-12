package com.example.demo.jpa.datacontrol.datachunk.controller;

import com.example.demo.jpa.datacontrol.datachunk.model.DataCompilation;
import com.example.demo.jpa.datacontrol.datachunk.service.DataChunkService;
import com.example.demo.jpa.jwt.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Slf4j
public class DataChunkController {
    private final DataChunkService dataChunkService;
    @GetMapping("/mydata/list")
    public ResponseEntity<?> myDataComp(HttpServletRequest request){
        Map<String, Object> userInfo = JwtUtil.getJwtRefreshTokenFromCookieAndParse(request.getCookies()).get(JwtUtil.claimName).asMap();


        log.info("claimUsername : " + userInfo.get(JwtUtil.claimUsername).toString());
        List<DataCompilation> check = dataChunkService.findMyDataCompilation(userInfo.get(JwtUtil.claimUsername).toString());
        log.info("mydata/list 비었냐? : " + check.stream().map(DataCompilation::toString).collect(Collectors.joining(", ")));

        return new ResponseEntity<>(dataChunkService.findMyDataCompilation(userInfo.get(JwtUtil.claimUsername).toString()), HttpStatus.OK);
    }

    @ExceptionHandler(NoSuchElementException.class)
    private ResponseEntity<?> noSuchElementException(NoSuchElementException e) {
        return new ResponseEntity<>("저장된 데이터 값이 없습니다.", HttpStatus.BAD_REQUEST);
    }
}
