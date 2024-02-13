package com.example.demo.datacontrol.dataclassroom.service;

import com.example.demo.datacontrol.dataclassroom.domain.dto.ClassroomSearchTypeProvideDto;
import com.example.demo.datacontrol.dataclassroom.domain.dto.ClassroomSequenceRequestDto;
import com.example.demo.datacontrol.dataclassroom.domain.entity.ClassroomChapter;
import com.example.demo.datacontrol.dataclassroom.domain.entity.ClassroomClass;
import com.example.demo.datacontrol.dataclassroom.domain.entity.ClassroomSequence;
import com.example.demo.datacontrol.dataclassroom.domain.entity.classroomsequencechunk.ClassroomSequenceChunk;
import com.example.demo.datacontrol.dataclassroom.repository.ClassroomClassCriteriaQuery;
import com.example.demo.datacontrol.dataclassroom.repository.ClassroomClassRepository;
import com.example.demo.user.model.entity.Educator;
import com.example.demo.user.model.entity.Student;
import com.example.demo.user.model.entity.User;
import com.example.demo.user.repository.UserRepository;
import com.example.demo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClassroomService {

    private final ClassroomClassRepository classroomClassRepository;
    private final ClassroomClassCriteriaQuery classroomClassCriteriaQuery;
    private final UserRepository userRepository;

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

        /*
        List<ClassroomSequenceChunk> item = dto.getItem();
        for (ClassroomSequenceChunk chunk : item) {
            ClassroomSequence classroomSequence = new ClassroomSequence(null, null, null, user.get(), classroomChapter);

        }

         */
        List<List<ClassroomSequenceChunk>> sequenceBlocks = dto.getSequenceBlocks();
        for (List<ClassroomSequenceChunk> chunks : sequenceBlocks) {
            ClassroomSequence classroomSequence = new ClassroomSequence(null, null, null, user.get(), classroomChapter);
            classroomSequence.updateClassroomSequenceChunk(chunks);
            classroomChapter.updateClassroomSequence(classroomSequence);

            for (ClassroomSequenceChunk chunk : chunks) {
                chunk.updateClassroomSequence(classroomSequence);
            }
        }
        classroomClassRepository.save(classroomClass);
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
