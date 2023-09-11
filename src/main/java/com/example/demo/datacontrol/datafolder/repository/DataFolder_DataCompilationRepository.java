package com.example.demo.datacontrol.datafolder.repository;

import com.example.demo.datacontrol.datafolder.model.DataFolder_DataCompilation;

import java.util.List;

public interface DataFolder_DataCompilationRepository {
    void saveDataFolder_DataCompilation(DataFolder_DataCompilation dataFolderDataCompilation);
    List<DataFolder_DataCompilation> findByDataFolderId(Long id);
}
