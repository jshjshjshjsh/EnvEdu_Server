package com.example.demo.survey.repository;

import com.example.demo.survey.domain.entity.SurveyData;
import com.example.demo.survey.domain.entity.SurveyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SurveyDataRepository extends JpaRepository<SurveyData, Long> {
    List<SurveyData> findAllBySurveyEntity(SurveyEntity surveyEntity);
}
