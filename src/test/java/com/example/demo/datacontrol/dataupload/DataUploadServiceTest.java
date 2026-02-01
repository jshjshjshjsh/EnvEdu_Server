package com.example.demo.datacontrol.dataupload;

import com.example.demo.datacontrol.datachunk.model.parent.DataEnumTypes;
import com.example.demo.datacontrol.dataupload.dto.DataUploadRequestDto;
import com.example.demo.datacontrol.dataupload.strategy.DataUploadStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DataUploadServiceTest {

    private DataUploadService dataUploadService;

    @Mock
    private DataUploadStrategy airQualityStrategy;
    @Mock
    private DataUploadStrategy seedStrategy;

    @BeforeEach
    void setUp() {
        //given(airQualityStrategy.getSupportedType()).willReturn(DataEnumTypes.AIRQUALITY);
        //given(seedStrategy.getSupportedType()).willReturn(DataEnumTypes.SEED);

        Map<DataEnumTypes, DataUploadStrategy> strategies = new HashMap<>();
        strategies.put(DataEnumTypes.AIRQUALITY, airQualityStrategy);
        strategies.put(DataEnumTypes.SEED, seedStrategy);

        dataUploadService = new DataUploadService(strategies);
    }

    @Test
    @DisplayName("AIRQUALITY 라벨이 들어오면 대기질 전략이 실행되어야 한다")
    void uploadAirQualityData() {
        // given
        String username = "testUser";
        DataUploadRequestDto dto = mock(DataUploadRequestDto.class);
        given(dto.getLabel()).willReturn("AIRQUALITY");

        // when
        dataUploadService.uploadData(dto, username);

        // then
        // 대기질 전략의 upload()가 1번 호출되었는지 검증
        verify(airQualityStrategy, times(1)).upload(dto, username);

        // 씨앗 전략은 호출되지 않았어야 함
        verify(seedStrategy, never()).upload(any(), any());
    }

    @Test
    @DisplayName("SEED 라벨이 들어오면 씨앗 전략이 실행되어야 한다")
    void uploadSeedData() {
        // given
        String username = "testUser";
        DataUploadRequestDto dto = mock(DataUploadRequestDto.class);
        given(dto.getLabel()).willReturn("SEED");

        // when
        dataUploadService.uploadData(dto, username);

        // then
        verify(seedStrategy, times(1)).upload(dto, username);
        verify(airQualityStrategy, never()).upload(any(), any());
    }

    @Test
    @DisplayName("지원하지 않는 라벨이 들어오면 예외가 터져야 한다")
    void uploadUnknownData() {
        // given
        String username = "testUser";
        DataUploadRequestDto dto = mock(DataUploadRequestDto.class);
        given(dto.getLabel()).willReturn("WEIRD_TYPE");

        // when & then
        assertThatThrownBy(() -> dataUploadService.uploadData(dto, username))
                .isInstanceOf(IllegalArgumentException.class);
    }
}