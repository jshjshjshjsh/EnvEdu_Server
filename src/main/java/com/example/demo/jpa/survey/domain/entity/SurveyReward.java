package com.example.demo.jpa.survey.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class SurveyReward {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String rewardUrl;
    private Boolean rewardedFlag;
    private String receiver;
    @ManyToOne
    private SurveyEntity surveyEntity;
    private LocalDateTime createTime;

    public void updateUsedReward(String receiver) {
        this.rewardedFlag = true;
        this.receiver = receiver;
    }

    public SurveyReward(String rewardUrl, Boolean rewardedFlag, String receiver, SurveyEntity surveyEntity, LocalDateTime createTime) {
        this.rewardUrl = rewardUrl;
        this.rewardedFlag = rewardedFlag;
        this.receiver = receiver;
        this.surveyEntity = surveyEntity;
        this.createTime = createTime;
    }
}
