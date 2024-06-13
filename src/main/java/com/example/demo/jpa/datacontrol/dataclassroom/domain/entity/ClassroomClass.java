package com.example.demo.jpa.datacontrol.dataclassroom.domain.entity;

import com.example.demo.jpa.datacontrol.dataclassroom.domain.types.ClassroomDataType;
import com.example.demo.jpa.datacontrol.dataclassroom.domain.types.ClassroomStudentGrade;
import com.example.demo.jpa.datacontrol.dataclassroom.domain.types.ClassroomSubjectType;
import com.example.demo.jpa.user.model.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class ClassroomClass extends Classroom{

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
    private String thumbnail;

    @OneToMany(mappedBy = "classroomClass", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClassroomChapter> classroomChapters = new ArrayList<>();

    public ClassroomClass(ClassroomStudentGrade grade, ClassroomSubjectType subject, ClassroomDataType dataType, String title, String subtitle, String description, User owner, String thumbnail) {
        super(title, subtitle, description, owner);
        this.grade = grade;
        this.subject = subject;
        this.dataType = dataType;
        this.thumbnail = thumbnail;
    }

    public void updateClassroomChapter(ClassroomChapter inputClassroomChapters){
        classroomChapters.add(inputClassroomChapters);
    }

    public void updateClassroomChapter(List<ClassroomChapter> inputClassroomChapters){
        classroomChapters.addAll(inputClassroomChapters);
    }

    public void updateLabels(){
        if(grade != null)
            gradeLabel = grade.label;
        if(subject != null)
            subjectLabel = subject.label;
        if(dataType != null)
            dataTypeLabel = dataType.label;
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
