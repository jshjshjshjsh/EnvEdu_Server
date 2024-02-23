package com.example.demo.datacontrol.dataclassroom.service;

import com.example.demo.datacontrol.datachart.domain.entity.CustomDataChart;
import com.example.demo.datacontrol.datachart.service.CustomDataChartService;
import com.example.demo.datacontrol.dataclassroom.domain.dto.ClassroomSearchTypeProvideDto;
import com.example.demo.datacontrol.dataclassroom.domain.dto.ClassroomSequenceRequestDto;
import com.example.demo.datacontrol.dataclassroom.domain.entity.ClassroomChapter;
import com.example.demo.datacontrol.dataclassroom.domain.entity.ClassroomClass;
import com.example.demo.datacontrol.dataclassroom.domain.entity.ClassroomSequence;
import com.example.demo.datacontrol.dataclassroom.domain.entity.classroomsequencechunk.ClassroomSequenceChunk;
import com.example.demo.datacontrol.dataclassroom.domain.types.ClassroomSequenceType;
import com.example.demo.datacontrol.dataclassroom.repository.ClassroomClassCriteriaQuery;
import com.example.demo.datacontrol.dataclassroom.repository.ClassroomClassRepository;
import com.example.demo.datacontrol.dataliteracy.model.dto.CustomDataDto;
import com.example.demo.datacontrol.dataliteracy.model.entity.CustomData;
import com.example.demo.datacontrol.dataliteracy.service.DataLiteracyService;
import com.example.demo.user.model.entity.Educator;
import com.example.demo.user.model.entity.Student;
import com.example.demo.user.model.entity.User;
import com.example.demo.user.repository.UserRepository;
import com.example.demo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ClassroomService {

    private final ClassroomClassRepository classroomClassRepository;
    private final ClassroomClassCriteriaQuery classroomClassCriteriaQuery;
    private final UserRepository userRepository;
    private final CustomDataChartService customDataChartService;
    private final DataLiteracyService dataLiteracyService;

    @Transactional
    public void generateClassroom(String username, ClassroomSequenceRequestDto dto){
        Optional<User> user = userRepository.findByUsername(username);
        if (user.get() instanceof Student)
            return;

        ClassroomClass classroomClass = dto.getClassroomClass();
        classroomClass.updateLabelToEnum();
        classroomClass.updateOwner(user.get());
        // 밑에서 classroomChapter, classroomSequence 소속 시켜야 함

        ClassroomChapter classroomChapter = dto.getClassroomChapter();
        classroomChapter.updateOwner(user.get());
        classroomClass.updateClassroomChapter(classroomChapter);

        List<List<ClassroomSequenceChunk>> sequenceBlocks = dto.getSequenceBlocks();
        List<CustomDataChart> customDataCharts = new ArrayList<>();
        List<CustomDataDto> customDatas = new ArrayList<>();
        Map<Integer, ClassroomSequence> classroomSequenceMap = new HashMap<>();
        Integer index = 0;
        for (List<ClassroomSequenceChunk> chunks : sequenceBlocks) {
            ClassroomSequence classroomSequence = new ClassroomSequence(null, null, null, user.get(), classroomChapter);
            classroomSequence.updateClassroomSequenceChunk(chunks);
            classroomChapter.updateClassroomSequence(classroomSequence);
            classroomSequenceMap.put(index, classroomSequence);
            index += 1;

            for (ClassroomSequenceChunk chunk : chunks) {
                chunk.updateClassroomSequence(classroomSequence);

                // Chart 저장 부분
                if (chunk.getClassroomSequenceType().equals(ClassroomSequenceType.CHART)) {
                    customDataCharts.add(new CustomDataChart(chunk.getTitle(), chunk.getLegendPosition(), chunk.getLabelPosition(),
                            user.get(), username, null, null, null, chunk.getChartType(), chunk.getUuid(), true,
                            chunk.getAxisProperties()));
                }
                if (chunk.getClassroomSequenceType().equals(ClassroomSequenceType.MATRIX)) {
                    customDatas.add(new CustomDataDto(CustomDataDto.parseStringToProperties(chunk.getProperties()), CustomDataDto.parseStringToData(chunk.getData()), null, null, LocalDateTime.now(), null, user.get(), null, null, null, false));

                }
            }
        }
        ClassroomClass save = classroomClassRepository.save(classroomClass);
        for (CustomDataChart c : customDataCharts) {
            // Chart 저장
            index = 0;
            for (int i = 0; i < save.getClassroomChapters().get(0).getClassroomSequences().size(); i++) {
                if (save.getClassroomChapters().get(0).getClassroomSequences().get(i).equals(classroomSequenceMap.get(index)))
                    c.updateClassroomIds(save.getId(), save.getClassroomChapters().get(0).getId(), save.getClassroomChapters().get(0).getClassroomSequences().get(i).getId());
                index += 1;
            }
            customDataChartService.createCustomDataChart(c, username);
        }
        for (CustomDataDto c : customDatas) {
            // CustomData 저장
            index = 0;
            for (int i = 0; i < save.getClassroomChapters().get(0).getClassroomSequences().size(); i++) {
                if (save.getClassroomChapters().get(0).getClassroomSequences().get(i).equals(classroomSequenceMap.get(index)))
                    c.updateClassroomIds(save.getId(), save.getClassroomChapters().get(0).getId(), save.getClassroomChapters().get(0).getClassroomSequences().get(i).getId());
                index += 1;
            }
            dataLiteracyService.uploadCustomData(c, username);
        }
    }

    @Transactional(readOnly = true)
    public ClassroomSearchTypeProvideDto getSearchTypes(){
        return new ClassroomSearchTypeProvideDto();
    }

    @Transactional(readOnly = true)
    public List<ClassroomClass> findAllClassroomByGradeSubjectDataType(String grade, String subject,
                                                                       String dataType) {

        List<ClassroomClass> allByGradeAndSubjectAndDataType = classroomClassCriteriaQuery.getClassroomClasses(grade, subject, dataType);
        return allByGradeAndSubjectAndDataType;
    }
}
