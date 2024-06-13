package com.example.demo.jpa.survey.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class SurveyAttribute {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String attributeName;
    @ManyToOne
    private SurveyEntity surveyEntity;

    public SurveyAttribute(String attributeName, SurveyEntity surveyEntity) {
        this.attributeName = attributeName;
        this.surveyEntity = surveyEntity;
    }
}
