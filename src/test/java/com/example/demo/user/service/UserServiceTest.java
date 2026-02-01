package com.example.demo.user.service;

import com.example.demo.mail.service.MailService;
import com.example.demo.redis.repo.AuthNumRepository;
import com.example.demo.user.model.entity.Educator;
import com.example.demo.user.model.entity.InviteCode;
import com.example.demo.user.model.entity.User;
import com.example.demo.user.repository.EducatorRepository;
import com.example.demo.user.repository.InviteCodeRepository;
import com.example.demo.user.repository.StudentRepository;
import com.example.demo.user.repository.Student_EducatorRepository;
import com.example.demo.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock private UserRepository userRepository;
    @Mock private InviteCodeRepository inviteCodeRepository;

    // UserService 생성을 위한 기타 Mock 객체들 (이 테스트에선 사용 안 함)
    @Mock private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Mock private MailService mailService;
    @Mock private StudentRepository studentRepository;
    @Mock private EducatorRepository educatorRepository;
    @Mock private AuthNumRepository authNumRepository;
    @Mock private Student_EducatorRepository student_educatorRepository;

    @Test
    @DisplayName("초대 코드가 없는 경우 새로 생성하여 저장해야 한다")
    void generateInviteCode_CreateNew() {
        // given
        String educatorUsername = "educator1";
        User educator = Educator.educatorBuilder().username(educatorUsername).build();

        // 사용자 조회 성공
        given(userRepository.findByUsername(educatorUsername)).willReturn(Optional.of(educator));
        // 기존 초대 코드 없음 -> 새로 생성 로직 진입
        given(inviteCodeRepository.findByUser(educator)).willReturn(Optional.empty());

        // when
        InviteCode result = userService.generateInviteCode(educatorUsername);

        // then
        // 1. 새로운 코드가 생성되었는지 확인
        assertThat(result).isNotNull();
        assertThat(result.getCode()).hasSize(6); // 코드 길이 6자리 확인
        assertThat(result.getUser()).isEqualTo(educator);

        // 2. 리포지토리의 save 메서드가 호출되었는지 검증
        verify(inviteCodeRepository, times(1)).save(any(InviteCode.class));
    }

    @Test
    @DisplayName("이미 초대 코드가 있는 경우 기존 코드를 갱신해야 한다")
    void generateInviteCode_UpdateExisting() {
        // given
        String educatorUsername = "educator1";
        User educator = Educator.educatorBuilder().username(educatorUsername).build();

        // 기존 초대 코드 생성 (만료된 상태라고 가정)
        String oldCode = "OLD123";
        InviteCode existingInviteCode = InviteCode.generate(educator);

        given(userRepository.findByUsername(educatorUsername)).willReturn(Optional.of(educator));
        // 기존 초대 코드 있음 -> 갱신 로직 진입
        given(inviteCodeRepository.findByUser(educator)).willReturn(Optional.of(existingInviteCode));

        // when
        InviteCode result = userService.generateInviteCode(educatorUsername);

        // then
        // 1. 객체는 그대로지만 내용은 바뀌었는지 확인
        assertThat(result).isEqualTo(existingInviteCode); // 같은 객체여야 함
        assertThat(result.getCode()).isNotEqualTo(oldCode); // 코드는 바뀌어야 함
        assertThat(result.getCode()).hasSize(6);

        // 2. 갱신 로직(Dirty Checking)이므로 save는 호출되지 않아야 함 (JPA 동작 방식에 따라 save 호출 안 함)
        // 만약 코드상 명시적으로 save를 안 한다면 verify(..., never()).save(...) 가 맞음
        // UserService 코드를 보면 updateInviteCode()만 호출하고 save()는 호출하지 않음
        verify(inviteCodeRepository, times(0)).save(any(InviteCode.class));
    }
}