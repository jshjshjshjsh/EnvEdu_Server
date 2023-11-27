package com.example.demo.datacontrol.dataliteracy.service;

import com.example.demo.datacontrol.dataliteracy.model.dto.CustomDataCopyRequest;
import com.example.demo.datacontrol.dataliteracy.model.dto.CustomDataDto;
import com.example.demo.datacontrol.dataliteracy.model.entity.CustomData;
import com.example.demo.datacontrol.dataliteracy.model.entity.CustomDataRedis;
import com.example.demo.datacontrol.dataliteracy.repository.CustomDataRepository;
import com.example.demo.redis.repo.CustomDataRedisRepository;
import com.example.demo.user.model.entity.Student_Educator;
import com.example.demo.user.model.entity.User;
import com.example.demo.user.repository.EducatorRepository;
import com.example.demo.user.repository.UserRepository;
import com.example.demo.user.service.UserService;
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
    private final EducatorRepository educatorRepository;
    private final UserService userService;

    @Transactional
    public void updateSingleSequenceCustomData(CustomDataDto customDataDto, String username){
        Optional<User> user = userRepository.findByUsername(username);
        customDataDto.updateOwner(user.get());
        customDataRepository.deleteAllByClassIdAndChapterIdAndSequenceIdAndOwner(customDataDto.getClassId(), customDataDto.getChapterId(),
                customDataDto.getSequenceId(), customDataDto.getOwner());
        customDataRepository.saveAll(customDataDto.convertDtoToEntity());
    }

    public List<CustomData> getSingleSequenceCustomData(Long classId,Long  chapterId,Long  sequenceId, String username){
        Optional<List<CustomData>> data = customDataRepository.findAllByClassIdAndChapterIdAndSequenceIdAndOwner(
                classId, chapterId, sequenceId, userRepository.findByUsername(username).get());

        return data.orElse(null);
    }

    public List<CustomData> getRelatedStudentsData(Long classId,Long  chapterId,Long  sequenceId, String educatorName){
        List<Student_Educator> students = userService.findAllByEducator(educatorRepository.findByUsername(educatorName).get());
        List<CustomData> result = new ArrayList<>();
        for (Student_Educator s_e: students){
            Optional<List<CustomData>> find = customDataRepository.findAllByClassIdAndChapterIdAndSequenceIdAndOwner(
                    classId, chapterId, sequenceId, s_e.getStudent());
            result.addAll(find.get());
        }

        return result;
    }

    @Transactional
    public void copyCustomData(CustomDataCopyRequest target){
        // todo: 여기서 제약사항 있어야 할 듯,
        //  이미 owner_id와 class_id 조건으로 데이터가 있으면 => 원래 있던 데이터 제거하고 다시 넣기
        List<CustomData> result = target.getData().convertDtoToEntity();
        int customDataSize = result.size();

        for (int i = 0; i<target.getUsers().size()-1; i++){
            for (int j = 0; j<customDataSize; j++) {
                result.add(result.get(j).clone());
            }
        }

        int studentCnt = 0;
        UUID uuid = UUID.randomUUID();
        for (int i = 0; i< result.size(); i++){
            result.get(i).updateOwner(target.getUsers().get(studentCnt));
            result.get(i).updateUuid(uuid);

            if ((i+1)%customDataSize == 0){
                studentCnt += 1;
                uuid = UUID.randomUUID();
            }

        }
        customDataRepository.saveAll(result);
    }

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
                customDataList.add(new CustomData(properties, chunks[i].replaceAll("\\[|\\]", ""), findCustomData.get().getMemo(), uuid, now, user.get(),null,null,null));
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
        customDataRedisRepository.save(CustomDataRedis.of(randomBuilder.toString(), customDataDto.getProperties(), customDataDto.getData(), customDataDto.getMemo()));

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
        List<CustomData> customData = customDataDto.convertDtoToEntity();
        customDataRepository.saveAll(customData);
    }
}
