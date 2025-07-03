package com.example.address.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.address.entity.AddressEntity;
import com.example.address.type.ReferenceType;
import com.example.company.repository.CompanyAddressRepository;

@Component
public class AddressEntityRepositoryStrategy {
    @Autowired
    private CompanyAddressRepository companyAddressRepository;

    @SuppressWarnings("unchecked")
    public <T extends AddressEntity> AddressRepository<T> get(ReferenceType type) {
        return (AddressRepository<T>) companyAddressRepository;
    }
}
