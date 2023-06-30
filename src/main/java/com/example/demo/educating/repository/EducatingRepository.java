package com.example.demo.educating.repository;

import com.example.demo.educating.model.MeasuredUnit;
import com.example.demo.seed.model.Seed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EducatingRepository extends JpaRepository<MeasuredUnit, Long> {
}