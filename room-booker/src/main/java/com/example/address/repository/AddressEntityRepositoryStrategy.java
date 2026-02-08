package com.example.address.repository;

import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

import com.example.address.entity.AddressEntity;
import com.example.address.type.ReferenceType;
import com.example.center.repository.CenterAddressRepository;
import com.example.company.repository.CompanyAddressRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AddressEntityRepositoryStrategy {
    private final CompanyAddressRepository companyAddressRepository;

    private final CenterAddressRepository centerAddressRepository;

    @SuppressWarnings("unchecked")
    public <T extends AddressEntity> AddressRepository<T> get(@NotNull ReferenceType type) {
        return switch (type) {
            case COMPANY -> (AddressRepository<T>) companyAddressRepository;
            case CENTER -> (AddressRepository<T>) centerAddressRepository;
        };
    }
}
