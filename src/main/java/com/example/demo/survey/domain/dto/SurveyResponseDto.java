package com.example.demo.survey.domain.dto;

import com.example.demo.survey.domain.entity.SurveyAttribute;
import com.example.demo.survey.domain.entity.SurveyEntity;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class SurveyResponseDto {

    private String surveyName;
    private String inviteCode;
    private LocalDateTime createTime;
    private List<Map<Long, String>> attributeName = new ArrayList<>();

    public SurveyResponseDto(SurveyEntity surveyEntity, List<SurveyAttribute> surveyAttributes) {
        this.surveyName = surveyEntity.getSurveyName();
        this.inviteCode = surveyEntity.getInviteCode();
        this.createTime = surveyEntity.getCreateTime();

        for (SurveyAttribute findAttribute : surveyAttributes) {
            Map<Long, String> inputAttribute = new HashMap<>();
            inputAttribute.put(findAttribute.getId(), findAttribute.getAttributeName());

            attributeName.add(inputAttribute);
        }
    }

    public SurveyResponseDto(String surveyName, String inviteCode, LocalDateTime createTime) {
        this.surveyName = surveyName;
        this.inviteCode = inviteCode;
        this.createTime = createTime;
    }
}
