package com.example.center.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.center.entity.CenterEntity;
import com.example.company.entity.CompanyEntity;

@Repository
public interface CenterRepository extends JpaRepository<CenterEntity, Long> {
    boolean existsByName(String name);

    List<CenterEntity> findByCompany(CompanyEntity company);
}
