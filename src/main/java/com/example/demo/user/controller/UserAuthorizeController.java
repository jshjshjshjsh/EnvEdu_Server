package com.example.demo.user.controller;

import com.example.demo.user.dto.request.EducatorAuthenticationDTO;
import com.example.demo.user.dto.response.UserDto;
import com.example.demo.user.model.enumerate.IsAuthorized;
import com.example.demo.user.service.UserAuthorizeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserAuthorizeController {
    private final UserAuthorizeService userAuthenticationService;

    @GetMapping("/admin/user/list")
    public ResponseEntity<?> userList(){

        return new ResponseEntity<>(userAuthenticationService.findUser(), HttpStatus.OK);
    }

    @GetMapping("/admin/educator/accept")
    public ResponseEntity<?> educatorAccept(@RequestParam(name="scope", defaultValue = "") IsAuthorized scope){

        return new ResponseEntity<>(userAuthenticationService.findEducatorByNoAuthorized(scope), HttpStatus.OK);
    }

    @PostMapping("/admin/educator/accept")
    public ResponseEntity<?> educatorAuthenticationAccept(@RequestBody EducatorAuthenticationDTO educatorAuthenticationDTO) {
        userAuthenticationService.changeEducatorStatus(educatorAuthenticationDTO);

        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
