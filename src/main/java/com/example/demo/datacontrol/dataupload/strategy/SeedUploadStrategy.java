package com.example.demo.datacontrol.dataupload.strategy;

import com.example.demo.datacontrol.datachunk.model.parent.DataEnumTypes;
import com.example.demo.datacontrol.dataupload.dto.DataUploadRequestDto;
import com.example.demo.seed.model.Seed;
import com.example.demo.seed.service.SeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SeedUploadStrategy implements DataUploadStrategy {

    private final SeedService seedService;

    @Override
    public DataEnumTypes getSupportedType() {
        return DataEnumTypes.SEED;
    }

    @Override
    public void upload(DataUploadRequestDto dto, String username) {
        List<Seed> data = dto.getData().stream()
                .map(item -> Seed.of(username, item))
                .collect(Collectors.toList());
        seedService.saveData(data, dto.getMemo());
    }
}