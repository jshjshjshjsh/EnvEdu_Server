package com.example.demo.jpa.datacontrol.dataclassroom.repository;

import com.example.demo.jpa.datacontrol.dataclassroom.domain.entity.ClassroomClass;
import com.example.demo.jpa.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassroomClassRepository extends JpaRepository<ClassroomClass, Long> {
    List<ClassroomClass> findAllByOwner(User owner);
}
