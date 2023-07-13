package com.example.demo.educating.service;

import com.example.demo.educating.model.MeasuredUnit;
import com.example.demo.educating.repository.MeasuredUnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EducatingService {

    private final MeasuredUnitRepository measuredUnitRepository;

    public void saveMeasuredUnit(MeasuredUnit measuredUnit){
        measuredUnitRepository.save(measuredUnit);
    }
}
