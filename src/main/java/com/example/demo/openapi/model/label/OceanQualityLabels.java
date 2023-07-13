package com.example.demo.openapi.model.label;

import com.example.demo.openapi.dto.AirQualityDTO;
import com.example.demo.openapi.dto.OceanQualityDTO;
import com.example.demo.openapi.model.parent.AirQualityParent;
import com.example.demo.openapi.model.parent.OceanQualityParent;
import lombok.Getter;

import java.lang.reflect.Field;
import java.util.HashMap;

@Getter
public class OceanQualityLabels {
    public HashMap<String, String> labels = new HashMap<>();
    public OceanQualityLabels(){
        Field[] fields = OceanQualityParent.class.getDeclaredFields();
        for (int i =0; i< fields.length; i++) {
            labels.put(fields[i].getName(), OceanQualityDTO.values[i]);
        }
    }
}
