package com.example.demo.datacontrol.datafolder.service;

import com.example.demo.datacontrol.datachunk.model.parent.DataEnumTypes;
import com.example.demo.datacontrol.datafolder.dto.DataFolderDto;
import com.example.demo.datacontrol.datafolder.dto.DataFolder_DataCompilationDto;
import com.example.demo.datacontrol.datafolder.dto.DataItems;
import com.example.demo.datacontrol.datafolder.dto.DataToDataFolderDto;
import com.example.demo.datacontrol.datafolder.model.DataFolder;
import com.example.demo.datacontrol.datafolder.model.DataFolder_DataCompilation;
import com.example.demo.datacontrol.datafolder.repository.DataFolderRepository;
import com.example.demo.datacontrol.datafolder.repository.DataFolder_DataCompilationRepository;
import com.example.demo.datacontrol.dataliteracy.repository.CustomDataRepository;
import com.example.demo.openapi.repository.OpenApiRepository;
import com.example.demo.seed.repository.SeedRepository;
import com.example.demo.user.model.entity.User;
import com.example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DataFolderService {

    private final DataFolder_DataCompilationRepository dataFolder_dataCompilationRepository;
    private final DataFolderRepository dataFolderRepository;
    private final UserRepository userRepository;
    private final OpenApiRepository openApiRepository;
    private final SeedRepository seedRepository;
    private final CustomDataRepository customDataRepository;

    @Transactional
    public void deleteDataFolderItem(List<Long> id) {
        dataFolder_dataCompilationRepository.deleteAllById(id);

    }

    @Transactional
    public void store(DataToDataFolderDto dataToDataFolderDto){
        DataFolder_DataCompilation dto = new DataFolder_DataCompilation();
        dto.addDataFolder(dataFolderRepository.findById(dataToDataFolderDto.getFolderId()).get());

        for (Long id : dataToDataFolderDto.getDataId()){

            if (dataToDataFolderDto.getLabel().equals(DataEnumTypes.AIRQUALITY.name())) {
                dto.addAirQuality(openApiRepository.findAirQualityById(id));
            }
            else if (dataToDataFolderDto.getLabel().equals(DataEnumTypes.OCEANQUALITY.name())) {
                dto.addOceanQuality(openApiRepository.findOceanQualityById(id));
            }
            else if (dataToDataFolderDto.getLabel().equals(DataEnumTypes.SEED.name())) {
                dto.addSeed(seedRepository.findById(id).get());
            }
            if (dataToDataFolderDto.getLabel().equals(DataEnumTypes.CUSTOM.name())){
                dto.addCustomData(customDataRepository.findById(id).get());
            }
        }
        dto.addSaveDate(LocalDateTime.now());

        dataFolder_dataCompilationRepository.save(dto);
    }

    @Transactional
    public void deleteDataFolder(String username, DataFolderDto dataFolderDto){
        Optional<User> user = userRepository.findByUsername(username);
        dataFolderRepository.deleteByIdAndOwner(dataFolderDto.getParentId(), user.get());
    }

    @Transactional
    public void linkParentDataFolder(String username, DataFolderDto dataFolderDto){
        Optional<User> user = userRepository.findByUsername(username);
        Optional<DataFolder> parent = dataFolderRepository.findDataFolderByIdAndOwner(dataFolderDto.getParentId(), user.get());
        Optional<DataFolder> child = dataFolderRepository.findDataFolderByIdAndOwner(dataFolderDto.getChildId(), user.get());

        if (dataFolderDto.getParentId() == null )
            child.get().updateParentDataFolder(null);
        else if (parent.isPresent() && child.isPresent())
            child.get().updateParentDataFolder(parent.get());

    }

    @Transactional
    public void createDataFolder(String username, DataFolderDto dataFolderDto){
        Optional<User> user = userRepository.findByUsername(username);
        LocalDateTime now = LocalDateTime.now();
        DataFolder dataFolder = new DataFolder();
        dataFolder.updateDataFolder(dataFolderDto.getFolderName(), user.get(), now, now);
        dataFolderRepository.save(dataFolder);
    }

    private DataFolder_DataCompilationDto reassemble(List<DataFolder_DataCompilation> items){
        DataFolder_DataCompilationDto dto = new DataFolder_DataCompilationDto();
        List<DataItems> datas = dto.getData();

        for(DataFolder_DataCompilation item : items)
            datas.add(new DataItems(item.getSeed(), item.getAirQuality(), item.getOceanQuality(), item.getCustomData(), item.getSaveDate(), item.getId()));

        return dto;
    }

    public DataFolder_DataCompilationDto findByDataFolderCompilationId(Long id){
        List<DataFolder_DataCompilation> datas = dataFolder_dataCompilationRepository.findByDataFolderId(id);
        for (DataFolder_DataCompilation item: datas){
            item.getDataFolder().deleteThisParentDataFolder();
        }

        return reassemble(datas);
    }

    @Transactional
    public void updateDataFolderName(String username, DataFolderDto dataFolderDto) {
        Optional<User> user = userRepository.findByUsername(username);
        Optional<DataFolder> findDataFolder = dataFolderRepository.findDataFolderByIdAndOwner(dataFolderDto.getParentId(), user.get());
        findDataFolder.get().updateFolderName(dataFolderDto.getFolderName());
    }

    @Transactional(readOnly = true)
    public List<DataFolder> findDataFolderList(String username){
        Optional<User> user = userRepository.findByUsername(username);
        return dataFolderRepository.findAllByOwnerAndParentIsNull(user.get());
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
