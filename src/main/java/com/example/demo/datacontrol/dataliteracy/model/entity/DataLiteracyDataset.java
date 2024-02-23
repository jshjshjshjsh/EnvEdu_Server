package com.example.demo.datacontrol.dataliteracy.model.entity;

import com.example.demo.datacontrol.dataclassroom.domain.types.ClassroomDataType;
import com.example.demo.datacontrol.dataclassroom.domain.types.ClassroomStudentGrade;
import com.example.demo.datacontrol.dataclassroom.domain.types.ClassroomSubjectType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class DataLiteracyDataset {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    @Column(columnDefinition = "BINARY(16)")
    private UUID uuid;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private ClassroomStudentGrade grade;
    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private ClassroomSubjectType subject;
    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private ClassroomDataType dataType;
    @Transient
    private String gradeLabel;
    @Transient
    private String subjectLabel;
    @Transient
    private String dataTypeLabel;

    public DataLiteracyDataset(String title, String content, UUID uuid, String gradeLabel, String subjectLabel, String dataTypeLabel) {
        this.title = title;
        this.content = content;
        this.uuid = uuid;
        this.gradeLabel = gradeLabel;
        this.subjectLabel = subjectLabel;
        this.dataTypeLabel = dataTypeLabel;
    }

    public void updateLabelToEnum(){
        if(gradeLabel != null)
            grade = ClassroomStudentGrade.getByLabel(gradeLabel);
        if(subjectLabel != null)
            subject = ClassroomSubjectType.getByLabel(subjectLabel);
        if(dataTypeLabel != null)
            dataType = ClassroomDataType.getByLabel(dataTypeLabel);
    }
}
