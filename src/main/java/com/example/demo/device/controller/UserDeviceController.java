package com.example.demo.device.controller;

import com.example.demo.DTO.AddMACDTO;
import com.example.demo.DTO.MacListDTO;
import com.example.demo.DTO.ResponseDTO;
import com.example.demo.device.dto.request.DeviceUpdateDTO;
import com.example.demo.device.service.UserDeviceService;
import com.example.demo.jwt.model.JwtRefreshToken;
import com.example.demo.jwt.util.JwtUtil;
import com.example.demo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserDeviceController {
    private final UserDeviceService userDeviceService;
    private final UserService userService;

    @PostMapping("/admin/device")
    private ResponseEntity<?> addDevice(@Valid @RequestBody AddMACDTO addMACDTO) {
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

    @PostMapping("/user/device")
    private ResponseDTO<Object> registerDevice(@Valid @RequestBody AddMACDTO addMACDTO)
    {
        //userDeviceService.registerDevice(addMACDTO.getUsername(), addMACDTO.getMAC());
        return new ResponseDTO<>(HttpStatus.OK.value(), null);
    }

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
