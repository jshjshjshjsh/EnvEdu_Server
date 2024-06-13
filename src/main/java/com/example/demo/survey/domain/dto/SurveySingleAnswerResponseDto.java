package com.example.demo.survey.domain.dto;

import lombok.Getter;

@Getter
public class SurveySingleAnswerResponseDto {

    private Long surveyAttributeId;
    private String answer;

    public SurveySingleAnswerResponseDto(Long surveyAttributeId, String answer) {
        this.surveyAttributeId = surveyAttributeId;
        this.answer = answer;
    }
}
