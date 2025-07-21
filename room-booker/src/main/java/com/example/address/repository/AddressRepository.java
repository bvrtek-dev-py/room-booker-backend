package com.example.address.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.address.entity.AddressEntity;

public interface AddressRepository<T extends AddressEntity> extends JpaRepository<T, Long> {
    Optional<T> findByObjectId(Long objectId);
}