package com.example.demo.seed.repository;

import com.example.demo.seed.model.Seed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SeedRepository extends JpaRepository<Seed, Long> {
    List<Seed> findAllByMeasuredDateBetweenAndMacIn(LocalDateTime start, LocalDateTime end, List<String> MAC);
    List<Seed> findAllByMeasuredDateBetween(LocalDateTime start, LocalDateTime end);
}
