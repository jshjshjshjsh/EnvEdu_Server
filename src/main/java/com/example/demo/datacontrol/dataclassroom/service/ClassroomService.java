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
import com.example.demo.datacontrol.dataclassroom.repository.ClassroomObjectCriteriaQuery;
import com.example.demo.datacontrol.dataclassroom.repository.ClassroomClassRepository;
import com.example.demo.datacontrol.dataliteracy.model.dto.CustomDataDto;
import com.example.demo.datacontrol.dataliteracy.service.DataLiteracyService;
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
    private final ClassroomObjectCriteriaQuery classroomClassCriteriaQuery;
    private final UserService userService;
    private final UserRepository userRepository;
    private final ClassroomGenerationProcessor classroomProcessor;

    @Transactional(readOnly = true)
    public List<ClassroomClass> getMyRelatedClassroom(String username){
        User user = userRepository.findByUsername(username).get();

        if (user instanceof Student)
            user = userService.findEducatorByStudent((Student) user).getEducator();

        List<ClassroomClass> classroomClasses = classroomClassRepository.findAllByOwnerOptimization(user);
        for (ClassroomClass classroom : classroomClasses) {
            classroom.updateLabels();
        }

        return classroomClasses;
    }


    @Transactional(readOnly = true)
    public ClassroomClass getClassroomById(Long id){
        Optional<ClassroomClass> findClassroom = classroomClassRepository.findById(id);
        if (findClassroom.isPresent()) {
            findClassroom.get().updateLabels();
            return findClassroom.get();
        }

        return null;
    }

    @Transactional
    public void generateClassroom(String username, ClassroomSequenceRequestDto dto){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));
        if (user instanceof Student)
            return;

        // ClassroomClass 초기화
        ClassroomClass classroomClass = dto.getClassroomClass();
        classroomClass.generateInit(user);

        // ClassroomChapter 초기화
        ClassroomChapter classroomChapter = dto.getClassroomChapter();
        classroomClass.addClassroomChapter(classroomChapter);

        // ClassroomSequenceChunk 저장 작업
        var context = classroomProcessor.prepareSequenceBlocks(username, dto, user, classroomChapter);
        ClassroomClass savedClass = classroomClassRepository.save(classroomClass);

        // CustomDataChart 저장
        classroomProcessor.processCharts(username, context.getCustomDataCharts(), savedClass, context.getClassroomSequenceMap(), context.getCustomDataForChartList());

        // CustomData 저장
        classroomProcessor.processCustomData(username, context.getCustomDatas(), savedClass, context.getClassroomSequenceMap());
    }

    @Transactional(readOnly = true)
    public ClassroomSearchTypeProvideDto getSearchTypes(){
        return new ClassroomSearchTypeProvideDto();
    }

    @Transactional(readOnly = true)
    public List<ClassroomClass> findAllClassroomByGradeSubjectDataType(String grade, String subject,
                                                                       String dataType) {

        List<ClassroomClass> allByGradeAndSubjectAndDataType = classroomClassCriteriaQuery.getObjectByGradeAndSubjectAndDataType(grade, subject, dataType, ClassroomClass.class);

        for (ClassroomClass classroom : allByGradeAndSubjectAndDataType) {
            classroom.updateLabels();
        }
        return allByGradeAndSubjectAndDataType;
    }
}
