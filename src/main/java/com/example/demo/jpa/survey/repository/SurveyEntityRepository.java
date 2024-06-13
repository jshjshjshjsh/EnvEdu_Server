package com.example.demo.jpa.survey.repository;

import com.example.demo.jpa.admin.model.Admin;
import com.example.demo.jpa.survey.domain.entity.SurveyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SurveyEntityRepository extends JpaRepository<SurveyEntity, Long> {
    Optional<SurveyEntity> findByInviteCode(String inviteCode);
    List<SurveyEntity> findAllByAdmin(Admin admin);
}
