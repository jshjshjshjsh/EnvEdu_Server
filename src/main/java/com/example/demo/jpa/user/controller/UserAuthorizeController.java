package com.example.demo.jpa.user.controller;

import com.example.demo.jpa.user.service.UserAuthorizeService;
import com.example.demo.jpa.user.dto.request.EducatorAuthenticationDTO;
import com.example.demo.jpa.user.model.enumerate.IsAuthorized;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
