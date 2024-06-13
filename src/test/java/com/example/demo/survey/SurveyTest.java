package com.example.demo.survey;

import com.example.demo.admin.model.Admin;
import com.example.demo.admin.repository.AdminRepository;
import com.example.demo.survey.domain.dto.SurveyAnswerRequestDto;
import com.example.demo.survey.domain.entity.SurveyAttribute;
import com.example.demo.survey.domain.entity.SurveyData;
import com.example.demo.survey.domain.entity.SurveyEntity;
import com.example.demo.survey.repository.SurveyAttributeRepository;
import com.example.demo.survey.repository.SurveyDataRepository;
import com.example.demo.survey.repository.SurveyEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@SpringBootTest
public class SurveyTest {

    @Autowired
    private SurveyEntityRepository surveyEntityRepository;
    @Autowired
    private SurveyAttributeRepository surveyAttributeRepository;
    @Autowired
    private SurveyDataRepository surveyDataRepository;
    @Autowired
    private AdminRepository adminRepository;

    @Test
    @Transactional
    @Rollback(value = false)
    void surveySaveTest(){

        Optional<Admin> admin = adminRepository.findByUsername("admin");


        /** ======================================= 설문 포맷 저장 시작 ===================================== */
        /** 설문 조사 기준 저장 */
        SurveyEntity surveyEntity = new SurveyEntity("만족도 조사", admin.get(), LocalDateTime.now());
        surveyEntityRepository.save(surveyEntity);

        /** 설문 조사 속성 저장 */
        String[] attributes = {"안녕", "하세요", "만족", "하시나요?"};
        List<SurveyAttribute> surveyAttributes = new ArrayList<>();
        for (String attr : attributes) {
            surveyAttributes.add(new SurveyAttribute(attr, surveyEntity));
        }
        surveyAttributeRepository.saveAll(surveyAttributes);
        /** ======================================= 설문 포맷 저장 완료 ===================================== */


        /** ======================================= 설문 조사 시작 ===================================== */
        /** 설문 조사에 대한 응답 요청 */
        List<SurveyAttribute> findAttribute = surveyAttributeRepository.findBySurveyEntity_InviteCode(surveyEntity.getInviteCode());

        int answerIndex = 0;
        List<Map<Long, String>> dtoAttribute = new ArrayList<>();
        for (SurveyAttribute surveyAttribute : findAttribute) {
            Map<Long, String> attr = new HashMap<>();
            attr.put(surveyAttribute.getId(), String.valueOf(answerIndex));
            dtoAttribute.add(attr);

            answerIndex += 1;
        }
        SurveyAnswerRequestDto surveyRequestDto = new SurveyAnswerRequestDto(surveyEntity.getInviteCode(), dtoAttribute, "zolang2");

        /** 설문 요청 저장 */
        LocalDateTime now = LocalDateTime.now();
        List<SurveyData> surveyData = new ArrayList<>();

        for (Map<Long, String> requestData : surveyRequestDto.getAnswer()) {
            for (Map.Entry<Long, String> entry : requestData.entrySet()) {

                Optional<SurveyAttribute> findSurveyAttribute = surveyAttributeRepository.findById(entry.getKey());
                surveyData.add(new SurveyData(entry.getValue(), surveyRequestDto.getSender(), now, surveyEntity, findSurveyAttribute.get(), null));
            }
        }
        surveyDataRepository.saveAll(surveyData);
        /** ======================================= 설문 조사 종료 ===================================== */
    }

    @Test
    @Transactional
    @Rollback(value = false)
    void surveySaveTest2(){
        UUID uuid = UUID.randomUUID();

        Optional<Admin> admin = adminRepository.findByUsername("admin");

        /** ======================================= 설문 포맷 저장 시작 ===================================== */
        /** 설문 조사 기준 저장 */
        SurveyEntity surveyEntity = new SurveyEntity("만족도 조사", admin.get(), LocalDateTime.now());

        /** 설문 조사 속성 저장 */
        String[] attributes = {"안녕", "하세요", "만족", "하시나요?"};
        List<SurveyAttribute> surveyAttributes = surveyEntity.getAttribute();
        for (String attr : attributes) {
            surveyAttributes.add(new SurveyAttribute(attr, surveyEntity));
        }

        surveyEntityRepository.save(surveyEntity);
    }

    private String generateInviteCode(){

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
