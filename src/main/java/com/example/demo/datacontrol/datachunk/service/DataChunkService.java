package com.example.demo.datacontrol.datachunk.service;

import com.example.demo.datacontrol.datachunk.model.DataCompilation;
import com.example.demo.datacontrol.datachunk.repository.DataCompilationRepository;
import com.example.demo.user.model.entity.User;
import com.example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DataChunkService {
    private final DataCompilationRepository dataRepository;
    private final UserRepository userRepository;

    /*
    private List<DataCompilationDto> sortingMyDataCompilation(List<DataCompilation> items){
        List<DataCompilationDto> result = new ArrayList<>();
        LocalDateTime beforeTime = null;

        while(!items.isEmpty()){
            DataCompilationDto newData = new DataCompilationDto();
            List<DataSuperTypes> data = newData.getData();

            for(int i = 0; i<= items.size(); i++){
                DataCompilation item = items.get(0);
                if (beforeTime!= null && !beforeTime.isEqual(item.getSaveDate()))
                    break;
                data.add(new DataSuperTypes(item.getSeed(), item.getAirQuality(), item.getOceanQuality(), item.getSaveDate()));
                beforeTime = item.getSaveDate();
                items.remove(0);
                newData.addCount();
            }
            beforeTime = null;
            result.add(newData);
            newData.init();
        }

        return result;
    }
     */

    public List<DataCompilation> findMyDataCompilation(String username){
        Optional<User> user = userRepository.findByUsername(username);
        return dataRepository.findAllByOwnerIdOrderBySaveDate(user.get().getId());
    }
    @Transactional
    public void saveMyDataCompilation(UUID uuid, String dataLabel, User owner, LocalDateTime saveDate, int size){
        DataCompilation dataCompilation = new DataCompilation(owner, saveDate, dataLabel, uuid, size);

        dataRepository.save(dataCompilation);
    }
}
