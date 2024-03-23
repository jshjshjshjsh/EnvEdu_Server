package com.example.demo.datacontrol.dataclassroom.repository;

import com.example.demo.datacontrol.dataclassroom.domain.entity.answer.ClassroomAnswerDataType;
import com.example.demo.datacontrol.dataclassroom.domain.entity.answer.ClassroomAnswerTextData;
import com.example.demo.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClassroomAnswerTextDataRepository extends JpaRepository<ClassroomAnswerTextData, Long> {
    List<ClassroomAnswerTextData> findAllByClassIdAndChapterIdAndSequenceIdAndAnswerTypeAndCanShare(Long classId, Long ChapterId, Long SequenceId, ClassroomAnswerDataType answerType, Boolean canShare);
    List<ClassroomAnswerTextData> findAllByClassIdAndChapterIdAndSequenceIdAndAnswerTypeAndCanSubmit(Long classId, Long ChapterId, Long SequenceId, ClassroomAnswerDataType answerType, Boolean canSubmit);
    Optional<ClassroomAnswerTextData> findByClassIdAndChapterIdAndSequenceIdAndAnswerTypeAndOwner(Long classId, Long ChapterId, Long SequenceId, ClassroomAnswerDataType answerType, User owner);
    void deleteByClassIdAndChapterIdAndSequenceIdAndAnswerTypeAndOwner(Long classId, Long ChapterId, Long SequenceId, ClassroomAnswerDataType answerType, User owner);
}
