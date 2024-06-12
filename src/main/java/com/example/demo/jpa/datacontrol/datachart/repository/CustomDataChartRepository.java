package com.example.demo.jpa.datacontrol.datachart.repository;

import com.example.demo.jpa.datacontrol.datachart.domain.entity.CustomDataChart;
import com.example.demo.jpa.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomDataChartRepository extends JpaRepository<CustomDataChart, Long> {
    Optional<CustomDataChart> findByClassIdAndChapterIdAndSequenceIdAndOwnerAndCanSubmit(Long classId, Long chapterId, Long sequenceId, User owner, Boolean canSubmit);
    Optional<CustomDataChart> findByClassIdAndChapterIdAndSequenceIdAndOwnerAndCanShare(Long classId, Long chapterId, Long sequenceId, User owner, Boolean canShare);
    Optional<CustomDataChart> findByClassIdAndChapterIdAndSequenceIdAndOwner(Long classId, Long chapterId, Long sequenceId, User owner);
    List<CustomDataChart> findAllByClassIdAndChapterIdAndSequenceIdAndOwner(Long classId, Long chapterId, Long sequenceId, User owner);
}
