package com.example.apartment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.apartment.entity.ApartmentEntity;

@Repository
public interface ApartmentRepository extends JpaRepository<ApartmentEntity, Long> {}