package com.example.demo.datacontrol.dataclassroom.domain.entity;

import com.example.demo.datacontrol.dataclassroom.domain.types.ClassroomSequenceType;
import com.example.demo.user.model.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class ClassroomSequence extends Classroom {

    @Enumerated(EnumType.STRING)
    private ClassroomSequenceType classroomSequenceType;
    @ManyToOne
    @JsonIgnore
    private ClassroomChapter classroomChapter;

    public ClassroomSequence(String title, String subtitle, String description, User owner, ClassroomChapter classroomChapter, ClassroomSequenceType classroomSequenceType) {
        super(title, subtitle, description, owner);
        this.classroomChapter = classroomChapter;
        this.classroomSequenceType = classroomSequenceType;
    }
}
