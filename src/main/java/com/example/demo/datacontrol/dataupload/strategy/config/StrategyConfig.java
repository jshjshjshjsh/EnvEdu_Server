package com.example.demo.datacontrol.dataupload.strategy.config;

import com.example.demo.datacontrol.datachunk.model.parent.DataEnumTypes;
import com.example.demo.datacontrol.dataupload.strategy.DataUploadStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class StrategyConfig {

    @Bean
    public Map<DataEnumTypes, DataUploadStrategy> dataUploadStrategyMap(List<DataUploadStrategy> strategies) {
        return strategies.stream()
                .collect(Collectors.toMap(DataUploadStrategy::getSupportedType, strategy -> strategy));
    }
}