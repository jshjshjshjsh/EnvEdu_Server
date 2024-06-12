package com.example.demo.jpa.seed.repository;

import com.example.demo.jpa.seed.model.Seed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SeedRepository extends JpaRepository<Seed, Long> {
    List<Seed> findAllByDataUUIDAndUsername(UUID dataUUID, String username);

    List<Seed> findByMac(String mac);
    List<Seed> findAllByMeasuredDateBetweenAndMacIn(LocalDateTime start, LocalDateTime end, List<String> MAC);
    List<Seed> findAllByMeasuredDateBetween(LocalDateTime start, LocalDateTime end);
    Optional<Seed> findById(long id);
    void deleteById(Long id);
}
