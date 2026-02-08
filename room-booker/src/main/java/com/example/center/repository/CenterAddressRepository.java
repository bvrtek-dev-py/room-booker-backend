package com.example.center.repository;

import org.springframework.stereotype.Repository;

import com.example.address.repository.AddressRepository;
import com.example.center.entity.CenterAddressEntity;

@Repository
public interface CenterAddressRepository extends AddressRepository<CenterAddressEntity> {}
