package com.example.demo.datacontrol.dataupload;

import com.example.demo.datacontrol.datachunk.model.parent.DataEnumTypes;
import com.example.demo.datacontrol.dataliteracy.model.dto.CustomDataDto;
import com.example.demo.datacontrol.dataliteracy.service.DataLiteracyService;
import com.example.demo.datacontrol.dataupload.dto.DataUploadRequestDto;
import com.example.demo.datacontrol.dataupload.strategy.DataUploadStrategy;
import com.example.demo.openapi.model.entity.AirQuality;
import com.example.demo.openapi.model.entity.OceanQuality;
import com.example.demo.openapi.service.OpenApiService;
import com.example.demo.seed.model.Seed;
import com.example.demo.seed.service.SeedService;
import com.example.demo.user.model.entity.User;
import com.example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DataUploadService {

    private final Map<DataEnumTypes, DataUploadStrategy> strategies;

    public void uploadData(DataUploadRequestDto uploadedData, String username){

        DataEnumTypes type;
        try {
            type = DataEnumTypes.valueOf(uploadedData.getLabel());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("지원하지 않는 데이터 타입입니다: " + uploadedData.getLabel());
        }

        DataUploadStrategy strategy = strategies.get(type);
        if (strategy == null) {
            throw new IllegalArgumentException("해당 데이터 타입에 대한 처리 로직이 없습니다: " + type);
        }

        strategy.upload(uploadedData, username);
    }
}
