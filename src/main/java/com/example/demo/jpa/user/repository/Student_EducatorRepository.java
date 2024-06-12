package com.example.demo.jpa.user.repository;

import com.example.demo.jpa.user.model.entity.Educator;
import com.example.demo.jpa.user.model.entity.Student;
import com.example.demo.jpa.user.model.entity.Student_Educator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Student_EducatorRepository extends JpaRepository<Student_Educator, Long> {
    List<Student_Educator> findAllByEducator(Educator educator);
    Student_Educator findByStudent(Student student);
}
