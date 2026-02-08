package com.example.user.repository;

import java.util.Optional;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.user.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByUsername(@NotNull String username);

    Optional<UserEntity> findByEmail(@NotNull String email);
}
