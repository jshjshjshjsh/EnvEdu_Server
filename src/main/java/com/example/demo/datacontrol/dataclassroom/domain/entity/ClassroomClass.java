package com.example.demo.datacontrol.dataclassroom.domain.entity;

import com.example.demo.user.model.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class ClassroomClass extends Classroom{

    @OneToMany(mappedBy = "classroomClass", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ClassroomChapter> classroomChapters = new ArrayList<>();

    public ClassroomClass(String title, String subtitle, String description, User owner) {
        super(title, subtitle, description, owner);
    }

    public void updateClassroomChapter(List<ClassroomChapter> inputClassroomChapters){
        classroomChapters.addAll(inputClassroomChapters);
    }
}
