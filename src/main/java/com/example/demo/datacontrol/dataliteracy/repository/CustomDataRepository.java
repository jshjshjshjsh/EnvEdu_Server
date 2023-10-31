package com.example.demo.datacontrol.dataliteracy.repository;

import com.example.demo.datacontrol.dataliteracy.model.entity.CustomData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomDataRepository extends JpaRepository<CustomData, Long> {
    Optional<List<CustomData>> findCustomDataByUuid(UUID uuid);

}
