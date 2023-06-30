package com.example.demo.educating.service;

import com.example.demo.educating.model.MeasuredUnit;
import com.example.demo.educating.repository.EducatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EducatingService {

    private final EducatingRepository educatingRepository;

    public void saveMeasuredUnit(MeasuredUnit measuredUnit){
        educatingRepository.save(measuredUnit);
    }
}
