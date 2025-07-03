package com.example.company.repository;

import org.springframework.stereotype.Repository;

import com.example.address.repository.AddressRepository;
import com.example.company.entity.CompanyAddressEntity;

@Repository
public interface CompanyAddressRepository extends AddressRepository<CompanyAddressEntity> {}
