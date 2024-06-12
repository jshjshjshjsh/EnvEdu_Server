package com.example.demo.jpa.survey.repository;

import com.example.demo.jpa.survey.domain.entity.SurveyAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SurveyAttributeRepository extends JpaRepository<SurveyAttribute, Long> {
    List<SurveyAttribute> findBySurveyEntity_InviteCode(String inviteCode);
}
