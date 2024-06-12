package com.example.demo.jpa.datacontrol.datafolder.repository;

import com.example.demo.jpa.datacontrol.datafolder.model.DataFolder;
import com.example.demo.jpa.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DataFolderRepository extends JpaRepository<DataFolder, Long> {
    List<DataFolder> findAllByOwnerAndParentIsNull(User owner);
    Optional<DataFolder> findById(Long id);
    Optional<DataFolder> findDataFolderByIdAndOwner(Long id, User owner);
    void deleteByIdAndOwner(Long id, User owner);
}
