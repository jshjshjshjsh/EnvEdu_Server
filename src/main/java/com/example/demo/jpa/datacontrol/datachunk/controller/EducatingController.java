package com.example.demo.jpa.datacontrol.datachunk.controller;

import com.example.demo.jpa.datacontrol.datachunk.model.MeasuredUnit;
import com.example.demo.jpa.datacontrol.datachunk.service.EducatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
public class EducatingController {

    private final EducatingService educatingService;

    @PostMapping("/educating/unit")
    public ResponseEntity<?> registerUnit(@RequestBody MeasuredUnit measuredUnit){
        measuredUnit.updateJoinDateTime();
        educatingService.saveMeasuredUnit(measuredUnit);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    private void illegalArgumentExceptionHandler(HttpServletResponse response)
    {
        response.setStatus(HttpStatus.NOT_FOUND.value());
    }
}
