package com.example.demo.user.repository;

import com.example.demo.user.model.entity.Educator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EducatorRepository extends JpaRepository<Educator, Long> {
}
