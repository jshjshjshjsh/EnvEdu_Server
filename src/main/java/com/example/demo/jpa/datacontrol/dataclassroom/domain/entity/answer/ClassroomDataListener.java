package com.example.demo.jpa.datacontrol.dataclassroom.domain.entity.answer;

import javax.persistence.PostLoad;

public class ClassroomDataListener {
    @PostLoad
    public void prePersist(Object classroomData) {
        ((ClassroomData)classroomData).updateUsername();
    }
}
