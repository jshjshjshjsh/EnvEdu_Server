package com.example.demo.chart.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChartService {
    public List<String> parseDataFromArray(String jsonData){

        String[] keyValuePairs = jsonData.split("&");
        List<String> values = new ArrayList<>();

        for (String pair : keyValuePairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length < 2)
                continue;
            String value = keyValue[1];
            values.add(value);
        }
        return values;
    }
}
