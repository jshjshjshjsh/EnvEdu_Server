package com.example.demo.survey.domain.dto;

import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
public class SurveyAnswerRequestDto {

    private String inviteCode;
    private List<Map<Long, String>> answer;
    private String sender;

    public SurveyAnswerRequestDto(String inviteCode, List<Map<Long, String>> answer, String sender) {
        this.inviteCode = inviteCode;
        this.answer = answer;
        this.sender = sender;
    }
}
