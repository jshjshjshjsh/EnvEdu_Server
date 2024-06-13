package com.example.demo.survey.service;

import com.example.demo.admin.model.Admin;
import com.example.demo.admin.repository.AdminRepository;
import com.example.demo.aws.service.AwsService;
import com.example.demo.survey.domain.dto.*;
import com.example.demo.survey.domain.entity.SurveyAttribute;
import com.example.demo.survey.domain.entity.SurveyData;
import com.example.demo.survey.domain.entity.SurveyEntity;
import com.example.demo.survey.domain.entity.SurveyReward;
import com.example.demo.survey.repository.SurveyDataRepository;
import com.example.demo.survey.repository.SurveyEntityRepository;
import com.example.demo.survey.repository.SurveyRewardRepository;
import com.example.demo.user.model.entity.User;
import com.example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SurveyService {

    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final SurveyEntityRepository surveyEntityRepository;
    private final SurveyRewardRepository surveyRewardRepository;
    private final SurveyDataRepository surveyDataRepository;
    private final AwsService awsService;

    @Transactional(readOnly = true)
    public List<SurveyAnswerResponseDto> getSurveyAnswers(String inviteCode){
        List<SurveyAnswerResponseDto> result = new ArrayList<>();
        Optional<SurveyEntity> surveyEntity = surveyEntityRepository.findByInviteCode(inviteCode);
        List<SurveyData> findSurveyData = surveyDataRepository.findAllBySurveyEntity(surveyEntity.get());

        UUID before = null;
        SurveyAnswerResponseDto item = null;

        for (SurveyData sd : findSurveyData) {

            if (!sd.getDataUUID().equals(before)){
                item = new SurveyAnswerResponseDto(sd.getSender(), sd.getSendTime());
                item.getAnswerList().add(new SurveySingleAnswerResponseDto(sd.getSurveyAttribute().getId(), sd.getValue()));
                before = sd.getDataUUID();
                result.add(item);
                continue;
            }

            item.getAnswerList().add(new SurveySingleAnswerResponseDto(sd.getSurveyAttribute().getId(), sd.getValue()));
            before = sd.getDataUUID();
        }

        return result;
    }

    @Transactional
    public String getSurveyRewardAWSImage(String inviteCode, String receiver){
        // 받아온 뒤
        Optional<SurveyEntity> findSurveyEntity = surveyEntityRepository.findByInviteCode(inviteCode);
        Optional<SurveyReward> findNotUsedSurveyReward = surveyRewardRepository.findFirstByRewardedFlagAndSurveyEntityOrderById(false, findSurveyEntity.get());

        // 수취 Flag 변경
        if (findNotUsedSurveyReward.isPresent()) {
            findNotUsedSurveyReward.get().updateUsedReward(receiver);
        }
        return findNotUsedSurveyReward.get().getRewardUrl();
    }

    @Transactional
    public List<String> saveFiles(String inviteCode, List<MultipartFile> multipartFiles) throws IOException {
        // s3에 저장
        List<String> savedFileUrls = awsService.saveMultiPartFiles("설문조사보상",multipartFiles);

        // 로컬 DB에 정보 저장
        Optional<SurveyEntity> findSurveyEntity = surveyEntityRepository.findByInviteCode(inviteCode);

        List<SurveyReward> surveyRewards = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();

        for (String url : savedFileUrls) {
            surveyRewards.add(new SurveyReward(url, false, null, findSurveyEntity.get(), now));
        }
        surveyRewardRepository.saveAll(surveyRewards);

        return savedFileUrls;
    }

    @Transactional(readOnly = true)
    public List<SurveyResponseDto> getSurveyList(String username){
        Optional<Admin> admin = adminRepository.findByUsername(username);
        List<SurveyResponseDto> result = new ArrayList<>();

        List<SurveyEntity> findSurveyEntity = surveyEntityRepository.findAllByAdmin(admin.get());
        findSurveyEntity.forEach(entity -> result.add(new SurveyResponseDto(entity.getSurveyName(), entity.getInviteCode(), entity.getCreateTime())) );
        
        return result;
    }

    @Transactional(readOnly = true)
    public SurveyResponseDto getSurveyEntityAndAttributes(String inviteCode){
        Optional<SurveyEntity> findSurveyEntity = surveyEntityRepository.findByInviteCode(inviteCode);

        return new SurveyResponseDto(findSurveyEntity.get(), findSurveyEntity.get().getAttribute());
    }

    @Transactional
    public String answerSurvey(String inviteCode, SurveyAnswerRequestDto surveyRequestDto, String username){
        //Optional<User> user = userRepository.findByUsername(username);

        // 답변 저장 로직
        LocalDateTime now = LocalDateTime.now();
        List<SurveyData> surveyData = new ArrayList<>();

        Optional<SurveyEntity> findSurveyEntity = surveyEntityRepository.findByInviteCode(inviteCode);
        UUID uuid = UUID.randomUUID();
        for (Map<Long, String> requestData : surveyRequestDto.getAnswer()) {
            for (Map.Entry<Long, String> entry : requestData.entrySet()) {

                surveyData.add(new SurveyData(entry.getValue(), surveyRequestDto.getSender(), now,
                        findSurveyEntity.get(), findSurveyEntity.get().getSurveyAttributeById(entry.getKey()), uuid));
            }
        }
        surveyDataRepository.saveAll(surveyData);

        // 보상 반환 로직
        try{
            return this.getSurveyRewardAWSImage(findSurveyEntity.get().getInviteCode(), surveyRequestDto.getSender());
        }catch (NoSuchElementException e){
            e.printStackTrace();
        }
        return null;
    }

    @Transactional
    public String createSurveyFormat(SurveyCreateRequestDto surveyCreateRequestDto, String username) {
        Optional<Admin> admin = adminRepository.findByUsername(username);

        // SurveyEntity 저장 로직
        SurveyEntity surveyEntity = new SurveyEntity(surveyCreateRequestDto.getSurveyName(), admin.get(), LocalDateTime.now());

        // SurveyAttribute 저장 로직
        List<SurveyAttribute> surveyAttributes = surveyEntity.getAttribute();
        for (String attr : surveyCreateRequestDto.getSurveyAttribute()) {
            surveyAttributes.add(new SurveyAttribute(attr, surveyEntity));
        }

        surveyEntityRepository.save(surveyEntity);
        return surveyEntity.getInviteCode();
    }
}
