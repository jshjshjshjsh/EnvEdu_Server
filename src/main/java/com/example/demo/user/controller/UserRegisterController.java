package com.example.demo.user.controller;

import com.auth0.jwt.interfaces.Claim;
import com.example.demo.DTO.ResponseDTO;
import com.example.demo.exceptions.CustomMailException;
import com.example.demo.exceptions.DuplicateAttributeException;
import com.example.demo.jwt.util.JwtUtil;
import com.example.demo.user.dto.request.EmailDTO;
import com.example.demo.user.dto.request.RegisterDTO;
import com.example.demo.user.dto.request.StudentAddDTO;
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

    /**
     * educator 관련 api
    */
    @PostMapping("/register/educator")
    private ResponseDTO<Object> registerEducator(@Valid @RequestBody RegisterDTO registerDTO)
    {
        /*if(userService.checkDuplicateUsernameAndEmail(registerDTO.getUsername(), registerDTO.getEmail()))
        {
            throw new IllegalArgumentException();
        }
        Random random = new Random();
        String randomAuthNum = String.format("%04d", random.nextInt(10000));

        Educator educator = Educator.educatorBuilder()
                .username(registerDTO.getUsername())
                .password(bCryptPasswordEncoder.encode(registerDTO.getPassword()))
                .email(registerDTO.getEmail())
                .role(Role.ROLE_EDUCATOR)
                .state(State.INACTIVE)
                .isAuthorized(IsAuthorized.NO)
                .build();
        RegisterAuthNum registerAuthNum = RegisterAuthNum.builder()
                .email(educator.getEmail())
                .registerAuthNum(randomAuthNum)
                .build();
        userService.addUser(educator,registerAuthNum);*/

        return new ResponseDTO<>(HttpStatus.OK.value(),null);
    }

    @PostMapping("/educator/student")
    private ResponseEntity<?> registerStudent(HttpServletRequest request, @RequestBody StudentAddDTO studentAddDTO) {
        Map<String, Object> user_info = JwtUtil.getJwtRefreshTokenFromCookieAndParse(request.getCookies()).get(JwtUtil.claimName).asMap();
        String username = user_info.get(JwtUtil.claimUsername).toString();
        userService.addStudent(username, studentAddDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 공용 api
    */
    @PatchMapping("/register/auth")
    private ResponseDTO<Object> confirmAuthentication(@RequestBody Map<String,String> map)
    {
        userService.confirmAuthentication(map.get("username"),map.get("email"),map.get("authNum"));
        return new ResponseDTO<>(HttpStatus.OK.value(), null);
    }
    @PostMapping("/register/resend")
    private ResponseDTO<Object> resendAuthNum(@RequestBody Map<String,String> map)
    {
        /*Random random = new Random();
        String randomAuthNum = String.format("%04d", random.nextInt(10000));

        RegisterAuthNum registerAuthNum = RegisterAuthNum.builder()
                .email(map.get("email"))
                .registerAuthNum(randomAuthNum)
                .build();
        userService.resendAuthNum(registerAuthNum);*/

        return new ResponseDTO<>(HttpStatus.OK.value(),null);
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

    /**
     * CustomHandshakeHandler
     */
    @PostMapping("/test/authup")
    private void makeAdmin(@RequestBody Map<String,String> mp)
    {
        userService.test_makeAdmin(mp.get("username"));
    }

    @GetMapping("/user/test")
    private String userTest()
    {
        return "userTest";
    }
}
