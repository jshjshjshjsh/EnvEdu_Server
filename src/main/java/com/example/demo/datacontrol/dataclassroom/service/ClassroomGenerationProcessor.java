package com.example.demo.datacontrol.dataclassroom.service;

import com.example.demo.datacontrol.datachart.domain.entity.CustomDataChart;
import com.example.demo.datacontrol.datachart.service.CustomDataChartService;
import com.example.demo.datacontrol.dataclassroom.domain.dto.ClassroomSequenceRequestDto;
import com.example.demo.datacontrol.dataclassroom.domain.entity.ClassroomChapter;
import com.example.demo.datacontrol.dataclassroom.domain.entity.ClassroomClass;
import com.example.demo.datacontrol.dataclassroom.domain.entity.ClassroomSequence;
import com.example.demo.datacontrol.dataclassroom.domain.entity.classroomsequencechunk.ClassroomSequenceChunk;
import com.example.demo.datacontrol.dataclassroom.domain.types.ClassroomSequenceType;
import com.example.demo.datacontrol.dataliteracy.model.dto.CustomDataDto;
import com.example.demo.datacontrol.dataliteracy.service.DataLiteracyService;
import com.example.demo.user.model.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Component
@RequiredArgsConstructor
public class ClassroomGenerationProcessor {

    private final DataLiteracyService dataLiteracyService;
    private final CustomDataChartService customDataChartService;

    public ClassroomGenerationContext prepareSequenceBlocks(String username, ClassroomSequenceRequestDto dto, User user, ClassroomChapter classroomChapter) {
        List<CustomDataDto> customDataForChartList = new ArrayList<>();
        List<List<ClassroomSequenceChunk>> sequenceBlocks = dto.getSequenceBlocks();
        List<CustomDataChart> customDataCharts = new ArrayList<>();
        List<CustomDataDto> customDatas = new ArrayList<>();
        Map<Integer, ClassroomSequence> classroomSequenceMap = new HashMap<>();

        int index = 0;
        for (List<ClassroomSequenceChunk> chunks : sequenceBlocks) {
            ClassroomSequence classroomSequence = new ClassroomSequence(null, null, null, user, classroomChapter, chunks);
            classroomSequence.updateClassroomSequenceChunk(chunks);
            classroomChapter.updateClassroomSequence(classroomSequence);

            classroomSequenceMap.put(index, classroomSequence);
            index++;

            for (ClassroomSequenceChunk chunk : chunks) {
                chunk.updateClassroomSequence(classroomSequence);

                // Chart 저장 부분 전처리
                if (chunk.getClassroomSequenceType().equals(ClassroomSequenceType.CHART)) {
                    UUID target_uuid = chunk.getUuid();
                    if (chunk.getData() != null && chunk.getProperties() != null) {
                        customDataForChartList.add(new CustomDataDto(
                                CustomDataDto.parseStringToProperties(chunk.getProperties()),
                                CustomDataDto.parseStringToData(chunk.getData()),
                                null, null, LocalDateTime.now(), null, user,
                                null, null, null, false, chunk.getCanShare(), chunk.getCanSubmit()));

                        // 임시 UUID 설정
                        target_uuid = UUID.fromString("00000000-0000-0000-0000-000000000111");
                    }
                    customDataCharts.add(new CustomDataChart(
                            chunk.getTitle(), chunk.getLegendPosition(), chunk.getLabelPosition(),
                            user, username, null, null, null, chunk.getChartType(), target_uuid, true,
                            chunk.getAxisProperties(), chunk.getCanShare(), chunk.getCanSubmit()));
                    chunk.deletePropertiesAndData();
                }

                // Matrix 저장 부분 전처리
                if (chunk.getClassroomSequenceType().equals(ClassroomSequenceType.MATRIX)) {
                    customDatas.add(new CustomDataDto(
                            CustomDataDto.parseStringToProperties(chunk.getProperties()),
                            CustomDataDto.parseStringToData(chunk.getData()),
                            null, null, LocalDateTime.now(), null, user,
                            null, null, null, false, chunk.getCanShare(), chunk.getCanSubmit()));
                }
            }
        }
        return new ClassroomGenerationContext(customDataForChartList, customDataCharts, customDatas, classroomSequenceMap);
    }

    public void processCharts(String username, List<CustomDataChart> customDataCharts, ClassroomClass savedClass, Map<Integer, ClassroomSequence> classroomSequenceMap, List<CustomDataDto> customDataForChartList) {
        int index = 0;
        for (CustomDataChart c : customDataCharts) {
            // 저장된 Class 구조에서 해당하는 Sequence 찾기
            ClassroomSequence targetSequence = classroomSequenceMap.get(index);

            Long classId = savedClass.getId();
            Long chapterId = savedClass.getClassroomChapters().get(0).getId();
            Long sequenceId = targetSequence.getId(); // 저장되면서 ID가 생성되었음

            c.updateClassroomIds(classId, chapterId, sequenceId);

            if (c.getUuid().equals(UUID.fromString("00000000-0000-0000-0000-000000000111"))){
                CustomDataDto dto = customDataForChartList.get(index);
                dto.updateClassroomIds(classId, chapterId, sequenceId);
                c.updateUuid(dataLiteracyService.uploadCustomData(dto, username));
            }

            CustomDataChart customDataChartSaved = customDataChartService.createCustomDataChart(c, username, false);

            for (ClassroomSequenceChunk chunk : targetSequence.getSequenceChunks()) {
                if (chunk.getClassroomSequenceType().equals(ClassroomSequenceType.CHART)) {
                    chunk.updateCustomDataChart(customDataChartSaved);
                }
            }
            index++;
        }
    }

    public void processCustomData(String username, List<CustomDataDto> customDatas, ClassroomClass savedClass, Map<Integer, ClassroomSequence> classroomSequenceMap) {
        int index = 0;
        for (CustomDataDto c : customDatas) {
            ClassroomSequence targetSequence = classroomSequenceMap.get(index);
            c.updateClassroomIds(savedClass.getId(), savedClass.getClassroomChapters().get(0).getId(), targetSequence.getId());

            dataLiteracyService.uploadCustomData(c, username);
            index++;
        }
    }

    @Getter
    @RequiredArgsConstructor
    public static class ClassroomGenerationContext {
        private final List<CustomDataDto> customDataForChartList;
        private final List<CustomDataChart> customDataCharts;
        private final List<CustomDataDto> customDatas;
        private final Map<Integer, ClassroomSequence> classroomSequenceMap;
    }
}