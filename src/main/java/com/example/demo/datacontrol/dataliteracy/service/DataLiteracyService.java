package com.example.demo.datacontrol.dataliteracy.service;

import com.example.demo.datacontrol.datachunk.model.parent.DataEnumTypes;
import com.example.demo.datacontrol.datachunk.service.DataChunkService;
import com.example.demo.datacontrol.datafolder.repository.DataFolderRepository;
import com.example.demo.datacontrol.dataliteracy.model.dto.CustomDataCopyRequest;
import com.example.demo.datacontrol.dataliteracy.model.dto.CustomDataDto;
import com.example.demo.datacontrol.dataliteracy.model.entity.CustomData;
import com.example.demo.datacontrol.dataliteracy.model.entity.CustomDataRedis;
import com.example.demo.datacontrol.dataliteracy.repository.CustomDataRepository;
import com.example.demo.redis.repo.CustomDataRedisRepository;
import com.example.demo.user.model.entity.Student;
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
    private final DataChunkService dataChunkService;
    private final DataFolderRepository dataFolderRepository;

    @Transactional(readOnly = true)
    public List<CustomData> getSharedCustomData(Long classId, Long chapterId, Long sequenceId, String username) {
        List<Student_Educator> students = userService.findStudentsByStudentOrEducator(username);
        List<CustomData> result = new ArrayList<>();

        for (Student_Educator s : students) {
            Optional<List<CustomData>> findCustomData = customDataRepository.findAllByClassIdAndChapterIdAndSequenceIdAndOwnerAndCanShared(classId, chapterId, sequenceId, s.getStudent(), true);
            result.addAll(findCustomData.get());
        }
        return result;
    }

    @Transactional(readOnly = true)
    public List<CustomData> getSubmittedCustomData(Long classId, Long chapterId, Long sequenceId, String username) {
        List<Student_Educator> students = userService.findStudentsByStudentOrEducator(username);
        List<CustomData> result = new ArrayList<>();

        for (Student_Educator s : students) {
            Optional<List<CustomData>> findCustomData = customDataRepository.findAllByClassIdAndChapterIdAndSequenceIdAndOwnerAndCanSubmit(classId, chapterId, sequenceId, s.getStudent(), true);
            result.addAll(findCustomData.get());
        }
        return result;
    }

    @Transactional
    public void updateSequenceDataSubmit(CustomDataDto customDataDto, String username){
        Optional<User> user = userRepository.findByUsername(username);
        Optional<List<CustomData>> data = customDataRepository.findAllByClassIdAndChapterIdAndSequenceIdAndOwner(
                customDataDto.getClassId(), customDataDto.getChapterId(), customDataDto.getSequenceId(), user.get());
        for (CustomData c : data.get()) {
            c.updateIsSubmit(customDataDto.getIsSubmit());
            if (customDataDto.getIsSubmit() == null)
                c.updateIsSubmit(false);
        }
    }

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

    public List<CustomData> getRelatedStudentsData(Long classId,Long  chapterId,Long  sequenceId, String username){
        List<Student_Educator> students = userService.findStudentsByStudentOrEducator(username);

        List<CustomData> result = new ArrayList<>();
        for (Student_Educator s_e: students){
            Optional<List<CustomData>> find = customDataRepository.findAllByClassIdAndChapterIdAndSequenceIdAndOwnerAndCanSubmit(
                    classId, chapterId, sequenceId, s_e.getStudent(), true);
            result.addAll(find.get());
        }

        return result;
    }


    @Transactional
    public void copyCustomData(CustomDataCopyRequest target, String educator){
        // todo: 여기서 제약사항 있어야 할 듯,
        //  이미 owner_id와 class_id 조건으로 데이터가 있으면 => 원래 있던 데이터 제거하고 다시 넣기

        // todo: 20231205 ==> DataFolder, DataCompilation 를 만들어서 소속 시켜줘야 함
        //  dataChunkService.saveMyDataCompilation(); 를 사용해서 DataFolder에 넣어줘야 함
        /* 데이터를 배포한 교사의 데이터로 저장 => 기준이 되는 데이터로 만들기 위함 */
        Optional<User> findEducator = userRepository.findByUsername(educator);
        target.getData().updateOwner(findEducator.get());
        List<CustomData> data = target.getData().convertDtoToEntity();
        customDataRepository.deleteAllByClassIdAndChapterIdAndSequenceIdAndOwner(target.getData().getClassId(), target.getData().getChapterId(),
                target.getData().getSequenceId(), findEducator.get());
        customDataRepository.saveAll(data);


        /* 배포된 데이터를 학생들의 데이터로 저장 */
        for (Student s: target.getUsers()){
            Optional<User> user = userRepository.findByUsername(s.getUsername());
            CustomDataDto customDataDto = target.getData();
            customDataDto.updateOwner(user.get());
            customDataRepository.deleteAllByClassIdAndChapterIdAndSequenceIdAndOwner(customDataDto.getClassId(), customDataDto.getChapterId(),
                    customDataDto.getSequenceId(), customDataDto.getOwner());
        }

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
            result.get(i).addUuid(uuid);



            if ((i+1)%customDataSize == 0){
                /* DataCompilation에 저장해서 MyData에서 조회되게 추가 */
                /*
                dataChunkService.saveMyDataCompilation(uuid, "CUSTOM", target.getUsers().get(studentCnt), result.get(i).getSaveDate(),
                        target.getData().getData().size(), target.getData().getMemo());

                 */

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
            String axisTypes = findCustomData.get().getAxisTypes().replaceAll("\\[|\\]", "").trim();
            String[] chunks = findCustomData.get().getData().replaceAll("^\\[|\\]$", "").split("\\], \\[");

            UUID uuid = UUID.randomUUID();
            LocalDateTime now = LocalDateTime.now();

            for (int i = 0; i < chunks.length; i++) {
                customDataList.add(new CustomData(properties, chunks[i].replaceAll("\\[|\\]", ""), axisTypes, findCustomData.get().getMemo(), uuid, now, user.get(),null,null,null, false, false, false));
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
        customDataRedisRepository.save(CustomDataRedis.of(randomBuilder.toString(), customDataDto.getProperties(),
                customDataDto.getData(), customDataDto.getAxisTypes(), customDataDto.getMemo(), customDataDto.getIsSubmit()));

        return randomBuilder.toString();
    }

    public List<CustomData> getCustomDataList(String username){
        Optional<User> user = userRepository.findByUsername(username);
        List<CustomData> customDataList = customDataRepository.findAllByOwner(user.get());
        int index = 1;
        while (index < customDataList.size()){
            if (customDataList.get(index-1).getDataUUID().equals(customDataList.get(index).getDataUUID())){
                customDataList.remove(index);
                index -= 1;
            }
            index += 1;
        }
        return customDataList;
    }

    public CustomDataDto downloadCustomData(UUID uuid){
        Optional<List<CustomData>> customDataByUuid = customDataRepository.findCustomDataByDataUUID(uuid);
        CustomDataDto customDataDto = new CustomDataDto();
        customDataByUuid.ifPresent(customDataDto::convertCustomDataToDto);
        return customDataDto;
    }

    @Transactional
    public UUID uploadCustomData(CustomDataDto customDataDto, String username) {
        User user = null;
        Optional<User> findUser = userRepository.findByUsername(username);
        if (findUser.isPresent()) {
            user = findUser.get();
        }

        List<CustomData> customDataList = customDataDto.convertDtoToEntity();
        LocalDateTime now = null;
        UUID uuid = null;

        for (CustomData customData : customDataList) {
            customData.updateOwner(user);
            now = customData.getSaveDate();
            uuid = customData.getDataUUID();
        }
        customDataRepository.saveAll(customDataList);
        dataChunkService.saveMyDataCompilation(uuid, DataEnumTypes.CUSTOM.name(), user,
                                                now, 1, customDataDto.getMemo());
        return customDataList.get(0).getDataUUID();
    }

    /*
    * 배포했던 교사(기준이 되는) 데이터 조회*/
    public List<CustomData> getBasedSingleSequenceCustomData(Long classId, Long chapterId, Long sequenceId, String studentName) {
        Optional<User> student = userRepository.findByUsername(studentName);
        Student_Educator educatorByStudent = null;
        if (student.isPresent() && student.get() instanceof Student){
            educatorByStudent = userService.findEducatorByStudent((Student) student.get());
        }
        return customDataRepository.findAllByClassIdAndChapterIdAndSequenceIdAndOwner(classId, chapterId, sequenceId, educatorByStudent.getEducator()).orElse(null);

    }
}
