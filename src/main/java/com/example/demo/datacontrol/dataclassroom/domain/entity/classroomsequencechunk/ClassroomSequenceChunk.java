package com.example.demo.datacontrol.dataclassroom.domain.entity.classroomsequencechunk;

import com.example.demo.datacontrol.dataclassroom.domain.entity.ClassroomSequence;
import com.example.demo.datacontrol.dataclassroom.domain.types.ClassroomSequenceType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class ClassroomSequenceChunk {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ClassroomSequence classroomSequence;

    @Enumerated(EnumType.STRING)
    private ClassroomSequenceType classroomSequenceType;
    private Boolean studentVisibleStatus;

    // H1, H2, 토론, 질문,
    private String title;
    private String content;

    // 사진, Youtube,
    private String url;

    // 표
    private String properties;
    private String data;

    public ClassroomSequenceChunk(ClassroomSequence classroomSequence, ClassroomSequenceType classroomSequenceType, Boolean studentVisibleStatus, String title, String content, String url, String properties, String data) {
        this.classroomSequence = classroomSequence;
        this.classroomSequenceType = classroomSequenceType;
        this.studentVisibleStatus = studentVisibleStatus;
        this.title = title;
        this.content = content;
        this.url = url;
        this.properties = properties;
        this.data = data;
    }

    public void updateClassroomSequence(ClassroomSequence classroomSequence){
        this.classroomSequence = classroomSequence;
    }
}
