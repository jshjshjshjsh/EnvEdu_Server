package com.example.demo.datacontrol.dataliteracy.service;

import com.example.demo.datacontrol.dataliteracy.model.dto.CustomDataDto;
import com.example.demo.datacontrol.dataliteracy.model.entity.CustomData;
import com.example.demo.datacontrol.dataliteracy.model.entity.CustomDataRedis;
import com.example.demo.datacontrol.dataliteracy.repository.CustomDataRepository;
import com.example.demo.redis.repo.CustomDataRedisRepository;
import com.example.demo.user.model.entity.User;
import com.example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DataLiteracyService {

    private final CustomDataRepository customDataRepository;
    private final CustomDataRedisRepository customDataRedisRepository;
    private final UserRepository userRepository;

    @Transactional
    public CustomDataDto joinCustomData(String username, String inviteCode){
        Optional<CustomDataRedis> findCustomData = customDataRedisRepository.findById(inviteCode);
        Optional<User> user = userRepository.findByUsername(username);
        List<CustomData> customDataList = new ArrayList<>();

        if(findCustomData.isPresent()) {
            String properties = findCustomData.get().getProperties().replaceAll("\\[|\\]", "").trim();
            String[] chunks = findCustomData.get().getData().replaceAll("^\\[|\\]$", "").split("\\], \\[");

            UUID uuid = UUID.randomUUID();
            LocalDateTime now = LocalDateTime.now();

            for (int i = 0; i < chunks.length; i++) {
                customDataList.add(new CustomData(properties, chunks[i].replaceAll("\\[|\\]", ""), inviteCode, uuid, now, user.get()));
            }

            customDataRepository.saveAll(customDataList);
            return this.downloadCustomData(uuid);
        }
        return null;
    }

    @Transactional
    public String generateInviteCustomData(CustomDataDto customDataDto){
        String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuilder randomBuilder = new StringBuilder(6);

        for (int i = 0; i < 6; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            randomBuilder.append(randomChar);
        }
        customDataRedisRepository.save(CustomDataRedis.of(randomBuilder.toString(), customDataDto.getProperties(), customDataDto.getData()));

        return randomBuilder.toString();
    }

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
