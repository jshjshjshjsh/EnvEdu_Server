package com.example.demo.user.repository;

import com.example.demo.user.model.entity.InviteCode;
import com.example.demo.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InviteCodeRepository extends JpaRepository<InviteCode, Long> {
    Optional<InviteCode> findByCode(String code);
    Optional<InviteCode> findByUser(User user);
}
