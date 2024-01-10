package com.example.demo.datacontrol.dataclassroom.repository;

import com.example.demo.datacontrol.dataclassroom.domain.entity.Classroom;
import com.example.demo.datacontrol.dataclassroom.domain.entity.ClassroomSequence;
import com.example.demo.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassroomSequenceRepository extends JpaRepository<ClassroomSequence, Long> {
}
