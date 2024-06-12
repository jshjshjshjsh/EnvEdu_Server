package com.example.demo.jpa.datacontrol.dataclassroom.domain.entity;

import com.example.demo.jpa.datacontrol.dataclassroom.domain.entity.classroomsequencechunk.ClassroomSequenceChunk;
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
public class ClassroomSequence extends Classroom {

    @OneToMany(mappedBy = "classroomSequence", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    List<ClassroomSequenceChunk> sequenceChunks = new ArrayList<>();

    @ManyToOne
    @JsonIgnore
    private ClassroomChapter classroomChapter;

    public ClassroomSequence(String title, String subtitle, String description, User owner, ClassroomChapter classroomChapter) {
        super(title, subtitle, description, owner);
        this.classroomChapter = classroomChapter;
    }

    public void updateClassroomSequenceChunk(List<ClassroomSequenceChunk> inputClassroomSequenceChunks) {
        sequenceChunks.addAll(inputClassroomSequenceChunks);
    }

}
