package com.example.demo.datacontrol.dataliteracy.repository;

import com.example.demo.datacontrol.dataliteracy.model.entity.DataLiteracyDataset;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataLiteracyDatasetRepository extends JpaRepository<DataLiteracyDataset, Long> {
}
