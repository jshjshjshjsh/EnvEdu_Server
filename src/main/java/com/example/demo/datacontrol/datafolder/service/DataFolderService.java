package com.example.demo.datacontrol.datafolder.service;

import com.example.demo.datacontrol.datachunk.model.parent.DataSuperTypes;
import com.example.demo.datacontrol.datafolder.dto.DataFolder_DataCompilationDto;
import com.example.demo.datacontrol.datafolder.model.DataFolder;
import com.example.demo.datacontrol.datafolder.model.DataFolder_DataCompilation;
import com.example.demo.datacontrol.datafolder.repository.DataFolderRepository;
import com.example.demo.datacontrol.datafolder.repository.DataFolder_DataCompilationRepository;
import com.example.demo.user.model.entity.User;
import com.example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DataFolderService {

    private final DataFolder_DataCompilationRepository dataFolder_dataCompilationRepository;
    private final DataFolderRepository dataFolderRepository;
    private final UserRepository userRepository;
    private DataFolder_DataCompilationDto reassemble(List<DataFolder_DataCompilation> items){
        DataFolder_DataCompilationDto dto = new DataFolder_DataCompilationDto();
        List<DataSuperTypes> datas = dto.getData();

        for(DataFolder_DataCompilation item : items)
            datas.add(new DataSuperTypes(item.getSeed(), item.getAirQuality(), item.getOceanQuality(), item.getSaveDate()));

        return dto;
    }

    public DataFolder_DataCompilationDto findByDataFolderCompilationId(Long id){
        List<DataFolder_DataCompilation> datas = dataFolder_dataCompilationRepository.findByDataFolderId(id);
        for (DataFolder_DataCompilation item: datas){
            item.getDataFolder().deleteThisParentDataFolder();
        }

        return reassemble(datas);
    }
    public List<DataFolder> findByDataFolder(String username){
        Optional<User> user = userRepository.findByUsername(username);
        List<DataFolder> datas = dataFolderRepository.findAllById(user.get().getId());
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
