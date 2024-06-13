package com.example.demo.jpa.datacontrol.dataliteracy.repository;

import com.example.demo.jpa.datacontrol.dataliteracy.model.entity.CustomData;
import com.example.demo.jpa.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomDataRepository extends JpaRepository<CustomData, Long> {
    Optional<List<CustomData>> findCustomDataByDataUUID(UUID uuid);
    Optional<CustomData> findById(Long id);
    Optional<List<CustomData>> findAllByClassIdAndChapterIdAndSequenceIdAndOwner(Long classId, Long chapterId, Long sequenceId, User owner);
    Optional<List<CustomData>> findAllByClassIdAndChapterIdAndSequenceIdAndOwnerAndCanSubmit(Long classId, Long chapterId, Long sequenceId, User owner, Boolean canSubmit);
    Optional<List<CustomData>> findAllByClassIdAndChapterIdAndSequenceIdAndOwnerAndCanShared(Long classId, Long chapterId, Long sequenceId, User owner, Boolean canShare);
    List<CustomData> findAllByDataUUIDAndOwner(UUID uuid, User owner);
    void deleteAllByClassIdAndChapterIdAndSequenceIdAndOwner(Long classId, Long chapterId, Long sequenceId, User owner);
    List<CustomData> findAllByOwner(User user);
}
