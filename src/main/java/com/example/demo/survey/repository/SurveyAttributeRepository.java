package com.example.demo.survey.repository;

import com.example.demo.survey.domain.entity.SurveyAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SurveyAttributeRepository extends JpaRepository<SurveyAttribute, Long> {
    List<SurveyAttribute> findBySurveyEntity_InviteCode(String inviteCode);
}
