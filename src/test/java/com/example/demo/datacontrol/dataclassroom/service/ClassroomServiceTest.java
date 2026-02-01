package com.example.demo.datacontrol.dataclassroom.service;

import com.example.demo.datacontrol.datachart.service.CustomDataChartService;
import com.example.demo.datacontrol.dataclassroom.domain.dto.ClassroomSequenceRequestDto;
import com.example.demo.datacontrol.dataclassroom.domain.entity.ClassroomChapter;
import com.example.demo.datacontrol.dataclassroom.domain.entity.ClassroomClass;
import com.example.demo.datacontrol.dataclassroom.domain.entity.ClassroomSequence;
import com.example.demo.datacontrol.dataclassroom.domain.entity.classroomsequencechunk.ClassroomSequenceChunk;
import com.example.demo.datacontrol.dataclassroom.domain.types.ClassroomSequenceType;
import com.example.demo.datacontrol.dataclassroom.repository.ClassroomClassRepository;
import com.example.demo.datacontrol.dataclassroom.repository.ClassroomObjectCriteriaQuery;
import com.example.demo.datacontrol.dataliteracy.service.DataLiteracyService;
import com.example.demo.user.model.entity.Educator;
import com.example.demo.user.model.entity.User;
import com.example.demo.user.repository.UserRepository;
import com.example.demo.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClassroomServiceTest {

    @InjectMocks
    private ClassroomService classroomService;

    @Mock
    private ClassroomClassRepository classroomClassRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserService userService;
    @Mock
    private CustomDataChartService customDataChartService;
    @Mock
    private DataLiteracyService dataLiteracyService;
    @Mock
    private ClassroomObjectCriteriaQuery classroomObjectCriteriaQuery;
    @Mock
    private ClassroomGenerationProcessor classroomProcessor;

    @Test
    @DisplayName("클래스룸 생성 시 계층 구조(Class->Chapter->Sequence->Chunk)와 연관관계가 올바르게 설정되어야 한다.")
    void generateClassroom_ShouldAssembleHierarchyCorrectly() {
        // given 데이터 및 객체 준비
        String username = "teacher1";
        User teacher = Educator.educatorBuilder().username(username).build();

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(teacher));

        ClassroomSequenceRequestDto dto = new ClassroomSequenceRequestDto();
        ClassroomClass cClass = new ClassroomClass();
        ClassroomChapter cChapter = new ClassroomChapter();

        List<ClassroomSequenceChunk> chunkList = new ArrayList<>();
        chunkList.add(new ClassroomSequenceChunk(null, ClassroomSequenceType.H1, true, "Test Title", "Test Content", null, null, null));

        List<List<ClassroomSequenceChunk>> sequenceBlocks = new ArrayList<>();
        sequenceBlocks.add(chunkList);

        ReflectionTestUtils.setField(dto, "classroomClass", cClass);
        ReflectionTestUtils.setField(dto, "classroomChapter", cChapter);
        ReflectionTestUtils.setField(dto, "sequenceBlocks", sequenceBlocks);

        ClassroomGenerationProcessor.ClassroomGenerationContext emptyContext =
                new ClassroomGenerationProcessor.ClassroomGenerationContext(
                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new HashMap<>()
                );

        // when Mock 동작 정의

        when(classroomProcessor.prepareSequenceBlocks(any(), any(), any(), any()))
                .thenAnswer(invocation -> {
                    ClassroomChapter chapter = invocation.getArgument(3);

                    // 1. Sequence 생성
                    ClassroomSequence sequence = new ClassroomSequence(null, null, null, teacher, chapter, new ArrayList<>());

                    // 2. Chunks 추가 (dto.get... 대신 로컬 변수 사용)
                    List<ClassroomSequenceChunk> chunks = sequenceBlocks.get(0);
                    sequence.updateClassroomSequenceChunk(chunks);

                    // 3. 양방향 관계 설정
                    for (ClassroomSequenceChunk chunk : chunks) {
                        chunk.updateClassroomSequence(sequence);
                    }

                    // 4. Chapter -> Sequence 연결
                    chapter.updateClassroomSequence(sequence);

                    return emptyContext;
                });

        // Repository 저장 시 엔티티 반환 Mock
        when(classroomClassRepository.save(any(ClassroomClass.class))).thenAnswer(invocation -> {
            ClassroomClass argument = invocation.getArgument(0);
            ReflectionTestUtils.setField(argument, "id", 1L);
            // Chapter ID 설정
            if (!argument.getClassroomChapters().isEmpty()) {
                ReflectionTestUtils.setField(argument.getClassroomChapters().get(0), "id", 1L);
                // Sequence ID 설정
                if (!argument.getClassroomChapters().get(0).getClassroomSequences().isEmpty()) {
                    ReflectionTestUtils.setField(argument.getClassroomChapters().get(0).getClassroomSequences().get(0), "id", 1L);
                }
            }
            return argument;
        });

        // execute 서비스 메서드 실행
        classroomService.generateClassroom(username, dto);

        // then 검증
        ArgumentCaptor<ClassroomClass> captor = ArgumentCaptor.forClass(ClassroomClass.class);
        verify(classroomClassRepository).save(captor.capture());

        ClassroomClass savedClass = captor.getValue();

        // Root 검증
        assertThat(savedClass).isNotNull();
        assertThat(savedClass.getOwner()).isEqualTo(teacher);

        // Chapter 검증
        assertThat(savedClass.getClassroomChapters()).hasSize(1);
        ClassroomChapter savedChapter = savedClass.getClassroomChapters().get(0);
        assertThat(savedChapter.getClassroomClass()).isEqualTo(savedClass);

        // Sequence 검증
        assertThat(savedChapter.getClassroomSequences()).hasSize(1);
        ClassroomSequence savedSequence = savedChapter.getClassroomSequences().get(0);
        assertThat(savedSequence.getClassroomChapter()).isEqualTo(savedChapter);

        // Chunk 검증
        assertThat(savedSequence.getSequenceChunks()).hasSize(1);
        ClassroomSequenceChunk savedChunk = savedSequence.getSequenceChunks().get(0);
        assertThat(savedChunk.getClassroomSequence()).isEqualTo(savedSequence);
        assertThat(savedChunk.getTitle()).isEqualTo("Test Title");
    }
}