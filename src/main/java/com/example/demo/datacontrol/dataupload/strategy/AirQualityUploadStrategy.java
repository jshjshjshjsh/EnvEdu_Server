package com.example.demo.datacontrol.dataupload.strategy;

import com.example.demo.datacontrol.datachunk.model.parent.DataEnumTypes;
import com.example.demo.datacontrol.dataupload.dto.DataUploadRequestDto;
import com.example.demo.openapi.model.entity.AirQuality;
import com.example.demo.openapi.service.OpenApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AirQualityUploadStrategy implements DataUploadStrategy {

    private final OpenApiService openApiService;

    @Override
    public DataEnumTypes getSupportedType() {
        return DataEnumTypes.AIRQUALITY;
    }

    @Override
    public void upload(DataUploadRequestDto dto, String username) {
        List<AirQuality> data = dto.getData().stream()
                .map(AirQuality::from)
                .collect(Collectors.toList());

        openApiService.saveAirQuality(data, username, dto.getMemo());
    }
}
