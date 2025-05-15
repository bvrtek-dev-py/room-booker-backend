package com.example.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.company.entity.CompanyEntity;


@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {
    boolean existsByName(String name);
}