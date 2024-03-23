package com.example.demo.datacontrol.dataliteracy.service;

import com.example.demo.datacontrol.dataclassroom.repository.ClassroomObjectCriteriaQuery;
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
    private final ClassroomObjectCriteriaQuery classroomClassCriteriaQuery;

    @Transactional
    public void deleteDataset(Long id){
        dataLiteracyDatasetRepository.deleteById(id);
    }

    @Transactional
    public void dataSetUpload(DataLiteracyDatasetDto dto){
        UUID uuid = dataLiteracyService.uploadCustomData(dto, null);
        DataLiteracyDataset dataLiteracyDataset = new DataLiteracyDataset(dto.getTitle(), dto.getContent(), uuid, dto.getGradeLabel(), dto.getSubjectLabel(), dto.getDataTypeLabel());
        dataLiteracyDataset.updateLabelToEnum();

        dataLiteracyDatasetRepository.save(dataLiteracyDataset);
    }

    @Transactional(readOnly = true)
    public List<DataLiteracyDataset> getDatasetList(String grade, String subject, String dataType){
        List<DataLiteracyDataset> findDataSet = classroomClassCriteriaQuery.getObjectByGradeAndSubjectAndDataType(grade, subject, dataType, DataLiteracyDataset.class);
        for (DataLiteracyDataset dataset: findDataSet)
            dataset.updateEnumToLabel();
        return findDataSet;
    }
}
