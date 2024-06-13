package com.example.demo.jpa.datacontrol.dataclassroom.repository;

import com.example.demo.jpa.datacontrol.dataclassroom.domain.entity.ClassroomSequence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassroomSequenceRepository extends JpaRepository<ClassroomSequence, Long> {
}
