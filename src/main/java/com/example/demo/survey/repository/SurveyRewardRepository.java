package com.example.demo.survey.repository;

import com.example.demo.survey.domain.entity.SurveyEntity;
import com.example.demo.survey.domain.entity.SurveyReward;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SurveyRewardRepository extends JpaRepository<SurveyReward, Long> {
    Optional<SurveyReward> findFirstByRewardedFlagAndSurveyEntityOrderById(Boolean rewardedFlag, SurveyEntity surveyEntity);
}
