package com.example.company.factory;

import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

import com.example.address.dto.AddressCreateRequest;
import com.example.address.entity.AddressEntity;
import com.example.address.factory.AddressEntityFactory;
import com.example.company.entity.CompanyAddressEntity;

@Component
public class CompanyAddressEntityFactory implements AddressEntityFactory {
    @Override
    public AddressEntity make(@NotNull AddressCreateRequest request, @NotNull Long objectId) {
        return new CompanyAddressEntity(
            request.getStreet(),
            request.getCity(),
            request.getState(),
            request.getZipCode(),
            request.getCountry(),
            objectId
        );
    }
}
