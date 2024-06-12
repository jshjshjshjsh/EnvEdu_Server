package com.example.demo.jpa.survey.domain.entity;

import com.example.demo.jpa.admin.model.Admin;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Entity
@Getter
@NoArgsConstructor
public class SurveyEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String surveyName;
    private String inviteCode;
    @ManyToOne
    @JsonIgnore
    private Admin admin;
    private LocalDateTime createTime;
    @OneToMany(mappedBy = "surveyEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SurveyAttribute> attribute = new ArrayList<>();
    @OneToMany(mappedBy = "surveyEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SurveyData> data = new ArrayList<>();

    public SurveyEntity(String surveyName, Admin admin, LocalDateTime createTime) {
        this.surveyName = surveyName;
        this.admin = admin;
        this.createTime = createTime;
        this.inviteCode = generateInviteCode();
    }

    public SurveyEntity(String surveyName, String inviteCode, Admin admin, LocalDateTime createTime) {
        this.surveyName = surveyName;
        this.inviteCode = inviteCode;
        this.admin = admin;
        this.createTime = createTime;
    }

    public SurveyAttribute getSurveyAttributeById(Long id) {
        for (int i = 0; i < attribute.size(); i++) {
            if (attribute.get(i).getId() == id) {
                return attribute.get(i);
            }
        }
        return null; // Return -1 if the id is not found
    }

    public String generateInviteCode(){
        String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuilder randomBuilder = new StringBuilder(6);

        for (int i = 0; i < 6; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            randomBuilder.append(randomChar);
        }

        return randomBuilder.toString();
    }
}
