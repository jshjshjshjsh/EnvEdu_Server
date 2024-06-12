package com.example.demo.jpa.datacontrol.dataliteracy.repository;

import com.example.demo.jpa.datacontrol.dataliteracy.model.entity.DataLiteracyDataset;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataLiteracyDatasetRepository extends JpaRepository<DataLiteracyDataset, Long> {
}
