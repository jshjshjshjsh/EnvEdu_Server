package com.example.demo.datacontrol.dataclassroom.domain.entity;

import com.example.demo.user.model.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class ClassroomChapter extends Classroom {

    @ManyToOne
    @JsonIgnore
    private ClassroomClass classroomClass;
    @OneToMany(mappedBy = "classroomChapter", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ClassroomSequence> classroomSequences = new ArrayList<>();

    public ClassroomChapter(String title, String subtitle, String description, User owner, ClassroomClass classroomClass) {
        super(title, subtitle, description, owner);
        this.classroomClass = classroomClass;
    }

    public void updateClassroomSequence(ClassroomSequence inputClassroomSequence) {
        classroomSequences.add(inputClassroomSequence);
    }

    public void updateClassroomSequence(List<ClassroomSequence> inputClassroomSequences) {
        classroomSequences.addAll(inputClassroomSequences);
    }
}
