package com.example.demo.datacontrol.dataliteracy.controller;

import com.example.demo.datacontrol.dataliteracy.model.dto.CustomDataCopyRequest;
import com.example.demo.datacontrol.dataliteracy.model.dto.CustomDataDto;
import com.example.demo.datacontrol.dataliteracy.service.DataLiteracyService;
import com.example.demo.jwt.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class DataLiteracyController {

    private final DataLiteracyService dataLiteracyService;

    @PostMapping("/dataLiteracy/inviteStudent")
    public ResponseEntity<?> inviteStudent(@RequestBody CustomDataCopyRequest request){
        // List<User> 형태로 들어온 사람들한테 data 복제
        // CustomDataDto 에 들어온 값을 복제
        dataLiteracyService.copyCustomData(request);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Deprecated
    @GetMapping("/dataLiteracy/inviteData")
    public ResponseEntity<?> joinData(@RequestParam String inviteCode, HttpServletRequest request){
        Map<String, Object> userInfo = JwtUtil.getJwtRefreshTokenFromCookieAndParse(request.getCookies()).get(JwtUtil.claimName).asMap();
        //dataLiteracyService.joinCustomData(userInfo.get(JwtUtil.claimUsername).toString(), inviteCode);

        //return new ResponseEntity<>(dataLiteracyService.joinCustomData("Student1", inviteCode), HttpStatus.OK);
        return new ResponseEntity<>(dataLiteracyService.joinCustomData(userInfo.get(JwtUtil.claimUsername).toString(), inviteCode), HttpStatus.OK);
    }

    @Deprecated
    @PostMapping("/dataLiteracy/inviteData")
    public ResponseEntity<?> inviteData(@RequestBody CustomDataDto customDataDto){
        String code = dataLiteracyService.generateInviteCustomData(customDataDto);

        return new ResponseEntity<>(code, HttpStatus.OK);
    }

    @GetMapping("/dataLiteracy/customData/list")
    public ResponseEntity<?> getCustomDataList(){
        return new ResponseEntity<>(dataLiteracyService.getCustomDataList(), HttpStatus.OK);
    }

    @GetMapping("/dataLiteracy/customData/download/{uuid}")
    public ResponseEntity<?> downloadCustomData(@PathVariable UUID uuid){
        return new ResponseEntity<>(dataLiteracyService.downloadCustomData(uuid), HttpStatus.OK);
    }

    @PostMapping("/dataLiteracy/customData/upload")
    public ResponseEntity<?> uploadCustomData(@RequestBody CustomDataDto customDataDto){
        dataLiteracyService.uploadCustomData(customDataDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
