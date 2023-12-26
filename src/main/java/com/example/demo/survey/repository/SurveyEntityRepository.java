package com.example.demo.survey.repository;

import com.example.demo.admin.model.Admin;
import com.example.demo.survey.domain.entity.SurveyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SurveyEntityRepository extends JpaRepository<SurveyEntity, Long> {
    Optional<SurveyEntity> findByInviteCode(String inviteCode);
    List<SurveyEntity> findAllByAdmin(Admin admin);
}
