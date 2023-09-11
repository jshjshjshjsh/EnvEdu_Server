package com.example.demo.datacontrol.datachunk.service;

import com.example.demo.datacontrol.datachunk.model.MeasuredUnit;
import com.example.demo.datacontrol.datachunk.repository.MeasuredUnitRepository;
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
