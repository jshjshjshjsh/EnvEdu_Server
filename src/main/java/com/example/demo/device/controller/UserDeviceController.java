package com.example.demo.device.controller;

import com.example.demo.device.dto.request.AddMACDTO;
import com.example.demo.device.dto.request.DeviceUpdateDTO;
import com.example.demo.device.service.UserDeviceService;
import com.example.demo.jwt.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserDeviceController {
    private final UserDeviceService userDeviceService;

    @PostMapping("/admin/device")
    private ResponseEntity<?> addDevice(@RequestBody AddMACDTO addMACDTO) {
        userDeviceService.addDevice(addMACDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/admin/devices")
    private ResponseEntity<?> getAllDevices() {
        return new ResponseEntity<>(userDeviceService.getAllDevices(), HttpStatus.OK);
    }

    @PutMapping("/admin/device")
    private ResponseEntity<?> updateDevice(@RequestBody DeviceUpdateDTO deviceUpdateDTO) {
        userDeviceService.updateDevice(deviceUpdateDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/admin/device/{mac}")
    private ResponseEntity<?> deleteDevice(@PathVariable String mac) {
        userDeviceService.deleteDevice(mac);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 자신과 연관된 기기 목록 조회
     * 학생의 경우, 자신에게 등록된 기기
     * 교사의 경우, 자신의 기기 및 자신이 지도하는 학생에게 등록된 기기
     */
    @GetMapping("/user/device")
    private ResponseEntity<?> getMyDevices(HttpServletRequest request) {
        Map<String, Object> userInfo = JwtUtil.getJwtRefreshTokenFromCookieAndParse(request.getCookies()).get(JwtUtil.claimName).asMap();
        return new ResponseEntity<>(userDeviceService.getDeviceList(userInfo.get(JwtUtil.claimUsername).toString()), HttpStatus.OK);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<?> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    private ResponseEntity<?> illegalArgumentExceptionHandler(IllegalArgumentException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
