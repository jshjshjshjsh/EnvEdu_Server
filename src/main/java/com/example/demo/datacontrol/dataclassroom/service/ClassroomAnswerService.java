package com.example.demo.datacontrol.dataclassroom.service;

import com.example.demo.datacontrol.dataclassroom.domain.entity.answer.ClassroomAnswerDataType;
import com.example.demo.datacontrol.dataclassroom.domain.entity.answer.ClassroomAnswerTextData;
import com.example.demo.datacontrol.dataclassroom.repository.ClassroomAnswerTextDataRepository;
import com.example.demo.user.model.entity.Educator;
import com.example.demo.user.model.entity.Student_Educator;
import com.example.demo.user.model.entity.User;
import com.example.demo.user.repository.UserRepository;
import com.example.demo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClassroomAnswerService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final ClassroomAnswerTextDataRepository classroomAnswerTextDataRepository;

    @Transactional(readOnly = true)
    public List<ClassroomAnswerTextData> findSharedTextData(String username, Long classId, Long chapterId, Long sequenceId, ClassroomAnswerDataType dataType){
        /**
         * 1. 자신과 연관된 학생 데이터 들고오기   ->  이게 진짜 필요한가..?
         * 2. 해당 학생의 데이터, CCS 연관시켜서 결과에 addAll()
         * */
        /*
        List<ClassroomAnswerTextData> result = new ArrayList<>();
        List<Student_Educator> relatedStudents = userService.findStudentsByStudentOrEducator(username);
        for (Student_Educator student : relatedStudents){
            List<ClassroomAnswerTextData> findTextDAta = classroomAnswerTextDataRepository.findAllByClassIdAndChapterIdAndSequenceIdAndAnswerTypeAndCanShare(classId, chapterId, sequenceId, dataType, true);

        }
         */

        if (dataType == null)
            return classroomAnswerTextDataRepository.findAllByClassIdAndChapterIdAndSequenceIdAndCanShare(classId, chapterId, sequenceId, true);


        return classroomAnswerTextDataRepository.findAllByClassIdAndChapterIdAndSequenceIdAndAnswerTypeAndCanShare(classId, chapterId, sequenceId, dataType, true);
    }

    @Transactional(readOnly = true)
    public List<ClassroomAnswerTextData> findSubmittedTextData(String username, Long classId, Long chapterId, Long sequenceId, ClassroomAnswerDataType dataType){
        Optional<User> user = userRepository.findByUsername(username);

        if (!(user.get() instanceof Educator))
            return null;

        if (dataType == null)
            return classroomAnswerTextDataRepository.findAllByClassIdAndChapterIdAndSequenceIdAndCanSubmit(classId, chapterId, sequenceId, true);

        return classroomAnswerTextDataRepository.findAllByClassIdAndChapterIdAndSequenceIdAndAnswerTypeAndCanSubmit(classId, chapterId, sequenceId, dataType, true);
    }

    @Transactional
    public void deleteClassroomTextData(String username, ClassroomAnswerTextData answerTextData){
        Optional<User> user = userRepository.findByUsername(username);

        classroomAnswerTextDataRepository.deleteByClassIdAndChapterIdAndSequenceIdAndAnswerTypeAndOwner(answerTextData.getClassId(), answerTextData.getChapterId(), answerTextData.getSequenceId(), answerTextData.getAnswerType(), user.get());

    }

    @Transactional
    public void updateClassroomTextDataSubmitAndShare(String username, ClassroomAnswerTextData answerTextData){
        Optional<User> user = userRepository.findByUsername(username);

        Optional<ClassroomAnswerTextData> findTextData = classroomAnswerTextDataRepository.findByClassIdAndChapterIdAndSequenceIdAndAnswerTypeAndOwner(answerTextData.getClassId(), answerTextData.getChapterId(), answerTextData.getSequenceId(), answerTextData.getAnswerType(), user.get());
        findTextData.get().updateShareAndSubmit(answerTextData.getCanShare(), answerTextData.getCanSubmit());
    }

    @Transactional
    public void createAnswerTextData(String username, ClassroomAnswerTextData answerTextData) {
        Optional<User> user = userRepository.findByUsername(username);

        if (classroomAnswerTextDataRepository.findByClassIdAndChapterIdAndSequenceIdAndAnswerTypeAndOwner(answerTextData.getClassId(), answerTextData.getChapterId(), answerTextData.getSequenceId(), answerTextData.getAnswerType(), user.get()).isPresent())
            classroomAnswerTextDataRepository.deleteByClassIdAndChapterIdAndSequenceIdAndAnswerTypeAndOwner(answerTextData.getClassId(), answerTextData.getChapterId(), answerTextData.getSequenceId(), answerTextData.getAnswerType(), user.get());

        answerTextData.updateOwner(user.get());
        classroomAnswerTextDataRepository.save(answerTextData);
    }
}
