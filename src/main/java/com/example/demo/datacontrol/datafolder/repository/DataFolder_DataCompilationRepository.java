package com.example.demo.datacontrol.datafolder.repository;

import com.example.demo.datacontrol.datafolder.dto.DataFolder_DataCompilationDto;
import com.example.demo.datacontrol.datafolder.dto.DataItems;
import com.example.demo.datacontrol.datafolder.model.DataFolder_DataCompilation;

import java.util.List;

public interface DataFolder_DataCompilationRepository {
    void saveDataFolder_DataCompilation(DataFolder_DataCompilation dataFolderDataCompilation);
    List<DataItems> findByDataFolderId(Long id);
    DataFolder_DataCompilation findById(Long id);
    void save(DataFolder_DataCompilation dataFolderDataCompilation);
    void deleteAllById(List<Long> id);
}
