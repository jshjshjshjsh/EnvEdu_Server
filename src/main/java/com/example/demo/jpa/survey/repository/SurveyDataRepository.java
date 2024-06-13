package com.example.demo.jpa.survey.repository;

import com.example.demo.jpa.survey.domain.entity.SurveyData;
import com.example.demo.jpa.survey.domain.entity.SurveyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SurveyDataRepository extends JpaRepository<SurveyData, Long> {
    List<SurveyData> findAllBySurveyEntity(SurveyEntity surveyEntity);
}
