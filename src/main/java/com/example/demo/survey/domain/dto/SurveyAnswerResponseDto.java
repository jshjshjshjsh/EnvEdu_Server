package com.example.demo.survey.domain.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class SurveyAnswerResponseDto {

    private String sender;
    private LocalDateTime sendTime;
    private List<SurveySingleAnswerResponseDto> answerList = new ArrayList<>();

    public SurveyAnswerResponseDto(String sender, LocalDateTime sendTime) {
        this.sender = sender;
        this.sendTime = sendTime;
    }
}
