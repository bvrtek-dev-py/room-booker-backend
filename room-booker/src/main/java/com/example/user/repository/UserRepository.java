package com.example.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.user.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByUsername(String username);
}