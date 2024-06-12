package com.example.demo.jpa.survey.repository;

import com.example.demo.jpa.survey.domain.entity.SurveyReward;
import com.example.demo.jpa.survey.domain.entity.SurveyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SurveyRewardRepository extends JpaRepository<SurveyReward, Long> {
    Optional<SurveyReward> findFirstByRewardedFlagAndSurveyEntityOrderById(Boolean rewardedFlag, SurveyEntity surveyEntity);
}
