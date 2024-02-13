package com.example.demo.datacontrol.dataliteracy.service;

import com.example.demo.datacontrol.dataliteracy.model.dto.CustomDataDto;
import com.example.demo.datacontrol.dataliteracy.model.dto.DataLiteracyDatasetDto;
import com.example.demo.datacontrol.dataliteracy.model.entity.DataLiteracyDataset;
import com.example.demo.datacontrol.dataliteracy.repository.DataLiteracyDatasetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DataLiteracyDatasetService {
    private final DataLiteracyService dataLiteracyService;
    private final DataLiteracyDatasetRepository dataLiteracyDatasetRepository;

    @Transactional
    public void deleteDataset(Long id){
        dataLiteracyDatasetRepository.deleteById(id);
    }

    @Transactional
    public void dataSetUpload(DataLiteracyDatasetDto customDataDto){
        UUID uuid = dataLiteracyService.uploadCustomData(customDataDto, null);
        DataLiteracyDataset dataLiteracyDataset = new DataLiteracyDataset(customDataDto.getTitle(), customDataDto.getContent(), uuid);
        dataLiteracyDatasetRepository.save(dataLiteracyDataset);
    }

    @Transactional(readOnly = true)
    public List<DataLiteracyDataset> getDatasetList(){
        return dataLiteracyDatasetRepository.findAll();
    }
}
