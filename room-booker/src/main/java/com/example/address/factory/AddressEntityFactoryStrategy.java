package com.example.address.factory;

import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

import com.example.address.type.ReferenceType;
import com.example.center.factory.CenterAddressEntityFactory;
import com.example.company.factory.CompanyAddressEntityFactory;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AddressEntityFactoryStrategy {
    private final CompanyAddressEntityFactory companyAddressEntityFactory;

    private final CenterAddressEntityFactory centerAddressEntityFactory;

    public AddressEntityFactory get(@NotNull ReferenceType type) {
        return switch (type) {
            case COMPANY -> companyAddressEntityFactory;
            case CENTER -> centerAddressEntityFactory;
        };
    }
}
