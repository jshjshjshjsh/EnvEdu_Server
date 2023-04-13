package com.example.demo.user.repository;

import com.example.demo.user.model.entity.User;
import com.example.demo.user.model.enumerate.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsernameAndState(String username, State state);
    boolean existsByEmailAndState(String email, State state);
    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameAndEmail(String username, String email);
    Optional<User> findByUsernameAndState(String username, State state);
}
