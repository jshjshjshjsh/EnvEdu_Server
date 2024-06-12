package com.example.demo.jpa.datacontrol.dataupload;

import com.example.demo.jpa.datacontrol.dataupload.dto.DataUploadRequestDto;
import com.example.demo.jpa.jwt.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class DataUploadController {
    private final DataUploadService dataUploadService;
    @PostMapping("/dataupload")
    public ResponseEntity<?> uploadExcel(@RequestBody DataUploadRequestDto uploadedData, HttpServletRequest request) {
        Map<String, Object> userInfo = JwtUtil.getJwtRefreshTokenFromCookieAndParse(request.getCookies()).get(JwtUtil.claimName).asMap();
        dataUploadService.uploadData(uploadedData, userInfo.get(JwtUtil.claimUsername).toString());
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
    @PostMapping("/test/dataupload")
    public ResponseEntity<?> uploadExcelTest(@RequestBody DataUploadRequestDto uploadedData, HttpServletRequest request) {
        dataUploadService.uploadData(uploadedData, "Student1");
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
