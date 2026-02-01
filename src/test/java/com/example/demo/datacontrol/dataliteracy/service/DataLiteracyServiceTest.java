package com.example.demo.datacontrol.dataliteracy.service;

import com.example.demo.datacontrol.datachunk.service.DataChunkService;
import com.example.demo.datacontrol.datafolder.repository.DataFolderRepository;
import com.example.demo.datacontrol.dataliteracy.model.dto.CustomDataCopyRequest;
import com.example.demo.datacontrol.dataliteracy.model.dto.CustomDataDto;
import com.example.demo.datacontrol.dataliteracy.model.entity.CustomData;
import com.example.demo.datacontrol.dataliteracy.repository.CustomDataRepository;
import com.example.demo.redis.repo.CustomDataRedisRepository;
import com.example.demo.user.model.entity.Educator;
import com.example.demo.user.model.entity.Student;
import com.example.demo.user.model.entity.User;
import com.example.demo.user.repository.EducatorRepository;
import com.example.demo.user.repository.UserRepository;
import com.example.demo.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DataLiteracyServiceTest {

    @InjectMocks
    private DataLiteracyService dataLiteracyService;

    @Mock
    private CustomDataRepository customDataRepository;
    @Mock
    private UserRepository userRepository;

    @Mock
    private CustomDataRedisRepository customDataRedisRepository;
    @Mock
    private EducatorRepository educatorRepository;
    @Mock
    private UserService userService;
    @Mock
    private DataChunkService dataChunkService;
    @Mock
    private DataFolderRepository dataFolderRepository;

    @Test
    @DisplayName("교사의 데이터를 학생들에게 복제(배포)하는 기능이 정상 동작해야 한다")
    void copyCustomData() {
        // given
        String educatorUsername = "educator1";
        Educator educator = new Educator();

        Student student1 = Student.studentBuilder().username("student1").build();
        Student student2 = Student.studentBuilder().username("student2").build();
        List<Student> students = Arrays.asList(student1, student2); // 학생 2명

        CustomDataDto realDto = new CustomDataDto();
        realDto.updateClassroomIds(1L, 1L, 1L);
        CustomDataDto customDataDto = spy(realDto);

        List<CustomData> mockEntityList = new ArrayList<>();
        CustomData mockData = mock(CustomData.class);
        mockEntityList.add(mockData);

        doReturn(mockEntityList).when(customDataDto).convertDtoToEntity();

        CustomDataCopyRequest request = mock(CustomDataCopyRequest.class);
        given(request.getData()).willReturn(customDataDto);
        given(request.getUsers()).willReturn(students);

        given(userRepository.findByUsername(educatorUsername)).willReturn(Optional.of(educator));
        given(userRepository.findById(student1.getId())).willReturn(Optional.of(student1));
        given(userRepository.findById(student2.getId())).willReturn(Optional.of(student2));

        // when
        dataLiteracyService.copyCustomData(request, educatorUsername);

        // then
        // 1. 삭제 검증: 총 3번 (교사 1명 + 학생 2명)
        verify(customDataRepository, times(3)).deleteAllByClassIdAndChapterIdAndSequenceIdAndOwner(
                eq(1L), eq(1L), eq(1L), any());

        // 2. 저장 검증: 총 3번 (교사 1명 + 학생 2명)
        verify(customDataRepository, times(3)).saveAll(any());

        // 3. 변환 로직 검증: 총 3번
        verify(customDataDto, times(3)).convertDtoToEntity();
    }
}