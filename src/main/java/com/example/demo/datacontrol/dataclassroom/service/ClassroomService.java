package com.example.demo.datacontrol.dataclassroom.service;

import com.example.demo.datacontrol.dataclassroom.domain.dto.ClassroomSearchTypeProvideDto;
import com.example.demo.datacontrol.dataclassroom.domain.entity.ClassroomClass;
import com.example.demo.datacontrol.dataclassroom.repository.ClassroomClassCriteriaQuery;
import com.example.demo.datacontrol.dataclassroom.repository.ClassroomClassRepository;
import com.example.demo.user.model.entity.Educator;
import com.example.demo.user.repository.UserRepository;
import com.example.demo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassroomService {

    private final ClassroomClassCriteriaQuery classroomClassCriteriaQuery;

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
