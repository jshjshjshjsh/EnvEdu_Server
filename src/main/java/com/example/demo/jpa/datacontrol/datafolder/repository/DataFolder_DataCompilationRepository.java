package com.example.demo.jpa.datacontrol.datafolder.repository;

import com.example.demo.jpa.datacontrol.datafolder.model.DataFolder_DataCompilation;

import java.util.List;

public interface DataFolder_DataCompilationRepository {
    void saveDataFolder_DataCompilation(DataFolder_DataCompilation dataFolderDataCompilation);
    List<DataFolder_DataCompilation> findByDataFolderId(Long id);
    DataFolder_DataCompilation findById(Long id);
    void save(DataFolder_DataCompilation dataFolderDataCompilation);
    void deleteAllById(List<Long> id);
}
