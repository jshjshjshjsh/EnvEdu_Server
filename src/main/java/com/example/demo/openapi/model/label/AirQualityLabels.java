package com.example.demo.openapi.model.label;

import com.example.demo.openapi.dto.AirQualityDTO;
import com.example.demo.openapi.model.parent.AirQualityParent;
import lombok.Getter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class AirQualityLabels {
    public HashMap<String, String> labels = new HashMap<>();
    public AirQualityLabels(){
        Field[] fields = AirQualityParent.class.getDeclaredFields();
        for (int i =0; i< fields.length; i++) {
            labels.put(fields[i].getName(), AirQualityDTO.values[i]);
        }
    }
}
