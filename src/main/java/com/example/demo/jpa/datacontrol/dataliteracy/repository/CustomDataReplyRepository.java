package com.example.demo.jpa.datacontrol.dataliteracy.repository;

import com.example.demo.jpa.datacontrol.dataliteracy.model.entity.CustomDataReply;
import com.example.demo.jpa.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomDataReplyRepository extends JpaRepository<CustomDataReply, Long> {
    Optional<CustomDataReply> findByIdAndOwner(Long id, User owner);
    List<CustomDataReply> findAllByClassIdAndChapterIdAndSequenceIdAndOwner(Long classId, Long chapterId, Long sequenceId, User owner);
}
