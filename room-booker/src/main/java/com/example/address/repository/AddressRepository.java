package com.example.address.repository;

import java.util.Optional;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.address.entity.AddressEntity;

public interface AddressRepository<T extends AddressEntity> extends JpaRepository<T, Long> {
    Optional<T> findByObjectId(@NotNull Long objectId);
}
