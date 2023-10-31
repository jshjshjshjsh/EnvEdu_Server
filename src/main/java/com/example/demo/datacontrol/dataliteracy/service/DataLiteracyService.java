package com.example.demo.datacontrol.dataliteracy.service;

import com.example.demo.datacontrol.dataliteracy.model.dto.CustomDataDto;
import com.example.demo.datacontrol.dataliteracy.model.entity.CustomData;
import com.example.demo.datacontrol.dataliteracy.repository.CustomDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DataLiteracyService {

    private final CustomDataRepository customDataRepository;

    public List<CustomData> getCustomDataList(){
        List<CustomData> customDataList = customDataRepository.findAll();
        int index = 1;
        while (index < customDataList.size()){
            if (customDataList.get(index-1).getUuid().equals(customDataList.get(index).getUuid())){
                customDataList.remove(index);
                index -= 1;
            }
            index += 1;
        }
        return customDataList;
    }

    public CustomDataDto downloadCustomData(UUID uuid){
        Optional<List<CustomData>> customDataByUuid = customDataRepository.findCustomDataByUuid(uuid);
        CustomDataDto customDataDto = new CustomDataDto();
        customDataByUuid.ifPresent(customDataDto::convertCustomDataToDto);
        return customDataDto;
    }

    @Transactional
    public void uploadCustomData(CustomDataDto customDataDto) {
        List<CustomData> customData = customDataDto.convertPropertiesListToString();
        customDataRepository.saveAll(customData);
    }
}
