package com.example.demo.educating.controller;

import com.example.demo.educating.model.MeasuredUnit;
import com.example.demo.educating.service.EducatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
public class EducatingController {

    private final EducatingService educatingService;

    @PostMapping("/educating/unit")
    public ResponseEntity<?> registerUnit(MeasuredUnit measuredUnit){
        educatingService.saveMeasuredUnit(measuredUnit);

        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    private void illegalArgumentExceptionHandler(HttpServletResponse response)
    {
        response.setStatus(HttpStatus.NOT_FOUND.value());
    }
}
