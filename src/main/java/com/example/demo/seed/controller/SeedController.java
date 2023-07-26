package com.example.demo.seed.controller;

import com.example.demo.DTO.DataSaveDTO;
import com.example.demo.DTO.ResponseDTO;
import com.example.demo.jwt.util.JwtUtil;
import com.example.demo.seed.model.Seed;
import com.example.demo.seed.service.SeedService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequiredArgsConstructor
public class SeedController {
    private final SeedService seedService;

    /**
     * 테스트에 사용된 api
     * 사용자명, 날짜를 이용해 해당되는 데이터 조회
     * /test/fetch url에서 예시 확인할 수 있음
     * todo: ResponseEntity로 return 수정
     */
    @GetMapping("/user/fetch")
    private ResponseDTO<List<Seed>> fetchData(@RequestParam Map<String,String> mp)
    {
        String username = mp.get("username");
        String start = mp.get("startDate");
        String end = mp.get("endDate");

        LocalDateTime startDate = LocalDateTime.of(2000, Month.JANUARY, 1, 0, 0, 0);
        LocalDateTime endDate = LocalDateTime.now();

        if (start != null && end != null){
            startDate = LocalDateTime.parse(start, DateTimeFormatter.RFC_1123_DATE_TIME);
            endDate = LocalDateTime.parse(end, DateTimeFormatter.RFC_1123_DATE_TIME);
        }

        List<Seed> list = seedService.refactorSeedData(seedService.extendSeedData(seedService.getDataByDateAndUsername(startDate, endDate, username)));

        return new ResponseDTO<>(HttpStatus.OK.value(), list);
    }

    @PostMapping("/seed/save")
    private ResponseEntity<?> saveSingleSeed(@RequestBody Seed seed, HttpServletRequest request){
        Map<String, Object> userInfo = JwtUtil.getJwtRefreshTokenFromCookieAndParse(request.getCookies()).get(JwtUtil.claimName).asMap();

        seedService.saveSingleData(seed, userInfo.get(JwtUtil.claimUsername).toString());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * todo: url을 restful하게 수정
     * todo: 문자열 형태의 seed 데이터를 적절히 Seed.class로 변환해 저장 구현
     * todo: ResponseEntity로 return 수정
     */
    @PostMapping("/user/save")
    private ResponseDTO<Object> saveData(@RequestBody DataSaveDTO data)
    {
        List<Seed> list = new ArrayList<>();
        data.getData().forEach((elem)->{
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                Seed seed = objectMapper.readValue(elem, Seed.class);
                seed.setDate(LocalDateTime.parse(seed.getDateString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                list.add(seed);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
        seedService.saveData(list);
        return new ResponseDTO<>(HttpStatus.OK.value(), null);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    private void illegalArgumentExceptionHandler(HttpServletResponse response)
    {
        response.setStatus(HttpStatus.NOT_FOUND.value());
    }
}
