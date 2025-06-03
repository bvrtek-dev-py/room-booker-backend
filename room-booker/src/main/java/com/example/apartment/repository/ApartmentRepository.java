package com.example.apartment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.apartment.entity.ApartmentEntity;
import com.example.center.entity.CenterEntity;

@Repository
public interface ApartmentRepository extends JpaRepository<ApartmentEntity, Long> {
    List<ApartmentEntity> findByCenter(CenterEntity center);
}