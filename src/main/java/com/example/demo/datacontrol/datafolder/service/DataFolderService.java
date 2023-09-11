package com.example.demo.datacontrol.datafolder.service;

import com.example.demo.datacontrol.datafolder.model.DataFolder;
import com.example.demo.datacontrol.datafolder.model.DataFolder_DataCompilation;
import com.example.demo.datacontrol.datafolder.repository.DataFolderRepository;
import com.example.demo.datacontrol.datafolder.repository.DataFolder_DataCompilationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DataFolderService {

    private final DataFolder_DataCompilationRepository dataFolder_dataCompilationRepository;
    private final DataFolderRepository dataFolderRepository;

    public List<DataFolder_DataCompilation> findByDataFolderId(Long id){
        List<DataFolder_DataCompilation> datas = dataFolder_dataCompilationRepository.findByDataFolderId(id);
        for (DataFolder_DataCompilation item: datas){
            item.getDataFolder().deleteThisParentDataFolder();
        }
        return datas;
    }
    public List<DataFolder> findByDataFolder2(Long id){
        List<DataFolder> datas = dataFolderRepository.findAllById(id);
        for(DataFolder item: datas)
            item.deleteThisParentDataFolder();
        return datas;
    }


    @Transactional
    public void saveDataFolder(DataFolder dataFolder) {
        dataFolderRepository.save(dataFolder);
    }

    @Transactional
    public void saveDataCompilation(DataFolder_DataCompilation dataFolderDataCompilation){
        dataFolder_dataCompilationRepository.saveDataFolder_DataCompilation(dataFolderDataCompilation);
    }
}
