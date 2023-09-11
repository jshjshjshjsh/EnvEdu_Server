package com.example.demo.datacontrol.datafolder.repository;

import com.example.demo.datacontrol.datafolder.model.DataFolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataFolderRepository extends JpaRepository<DataFolder, Long> {
    List<DataFolder> findAllById(Long id);
}
