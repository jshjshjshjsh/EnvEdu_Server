package com.example.demo.survey.domain.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class SurveyCreateRequestDto {

    private String surveyName;
    private List<String> surveyAttribute;
}
