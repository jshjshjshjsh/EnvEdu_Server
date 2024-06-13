package com.example.demo.user.repository;

import com.example.demo.user.model.entity.Educator;
import com.example.demo.user.model.enumerate.IsAuthorized;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EducatorRepository extends JpaRepository<Educator, Long> {
    List<Educator> findAll();
    Optional<Educator> findByUsername(String username);
    Optional<Educator> findById(Long id);
    Optional<List<Educator>> findIdByIsAuthorized(IsAuthorized isAuthorized);
}
