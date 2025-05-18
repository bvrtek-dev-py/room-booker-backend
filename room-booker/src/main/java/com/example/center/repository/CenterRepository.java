package com.example.center.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.center.entity.CenterEntity;

@Repository
public interface CenterRepository extends JpaRepository<CenterEntity, Long> {
    boolean existsByName(String name);
}