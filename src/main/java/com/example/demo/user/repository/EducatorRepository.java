package com.example.demo.user.repository;

import com.example.demo.user.model.entity.Educator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EducatorRepository extends JpaRepository<Educator, Long> {
    Optional<Educator> findByUsername(String username);
}
