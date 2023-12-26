package com.example.demo.survey.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class SurveyData {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String value;
    private String sender;
    private LocalDateTime sendTime;
    @ManyToOne
    private SurveyEntity surveyEntity;
    @ManyToOne
    private SurveyAttribute surveyAttribute;

    public SurveyData(String value, String sender, LocalDateTime sendTime, SurveyEntity surveyEntity, SurveyAttribute surveyAttribute) {
        this.value = value;
        this.sender = sender;
        this.sendTime = sendTime;
        this.surveyEntity = surveyEntity;
        this.surveyAttribute = surveyAttribute;
    }
}
