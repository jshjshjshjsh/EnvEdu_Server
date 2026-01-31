package com.example.demo.datacontrol.dataclassroom.repository;

import com.example.demo.datacontrol.dataclassroom.domain.entity.ClassroomClass;
import com.example.demo.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClassroomClassRepository extends JpaRepository<ClassroomClass, Long> {
    // 리팩토링 전 메소드
    List<ClassroomClass> findAllByOwner(User owner);

    // 리팩토링 후 메소드
    @Query("SELECT DISTINCT c FROM ClassroomClass c " +
            "LEFT JOIN FETCH c.classroomChapters " +
            "WHERE c.owner = :owner")
    List<ClassroomClass> findAllByOwnerOptimization(@Param("owner") User owner);
}
