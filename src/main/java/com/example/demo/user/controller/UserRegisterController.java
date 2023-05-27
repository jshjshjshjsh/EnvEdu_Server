package com.example.demo.user.controller;

import com.example.demo.exceptions.CustomMailException;
import com.example.demo.exceptions.DuplicateAttributeException;
import com.example.demo.jwt.util.JwtUtil;
import com.example.demo.user.dto.request.EmailDTO;
import com.example.demo.user.dto.request.RegisterDTO;
import com.example.demo.user.dto.request.StudentAddDTO;
import com.example.demo.user.dto.response.Student_EducatorDTO;
import com.example.demo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;


@RequiredArgsConstructor
@RestController
public class UserRegisterController {
    private final UserService userService;

    /**
     * 일반 user, student 관련 api
    */
    @PostMapping("/auth")
    private ResponseEntity<?> sendAuthNum(@Valid @RequestBody EmailDTO emailDTO) {
        userService.sendAuthNum(emailDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/auth")
    private ResponseEntity<?> checkAuthNum(@RequestParam(value = "email") String email,
                                           @RequestParam(value = "authNum") String authNum) {
        userService.checkAuthNum(email, authNum);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/user")
    private ResponseEntity<?> register(@Valid @RequestBody RegisterDTO registerDTO) {
        userService.addUser(registerDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/educator/student")
    private ResponseEntity<?> registerStudent(HttpServletRequest request, @RequestBody StudentAddDTO studentAddDTO) {
        Map<String, Object> userInfo = JwtUtil.getJwtRefreshTokenFromCookieAndParse(request.getCookies()).get(JwtUtil.claimName).asMap();
        userService.addStudent(userInfo, studentAddDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/educator/student_educator")
    private ResponseEntity<Student_EducatorDTO> getEducatingStudents(HttpServletRequest request) {
        Map<String, Object> userInfo = JwtUtil.getJwtRefreshTokenFromCookieAndParse(request.getCookies()).get(JwtUtil.claimName).asMap();
        Student_EducatorDTO result = userService.getEducatingStudents(userInfo);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 예외처리
     */

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<?> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e)
    {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    private ResponseEntity<?> illegalArgumentExceptionHandler(IllegalArgumentException e)
    {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomMailException.class)
    private ResponseEntity<?> customMailExceptionHandler(CustomMailException e)
    {
       return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DuplicateAttributeException.class)
    private ResponseEntity<?> duplicateAttributeExceptionHandler(DuplicateAttributeException e) {
        return new ResponseEntity<>("중복되는 " + e.getAttribute() + "(이)가 있습니다", HttpStatus.BAD_REQUEST);
    }
}
