package com.example.demo.survey.repository;

import com.example.demo.survey.domain.entity.SurveyData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyDataRepository extends JpaRepository<SurveyData, Long> {
}
