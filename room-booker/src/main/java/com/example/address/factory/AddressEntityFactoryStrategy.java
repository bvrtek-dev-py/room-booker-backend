package com.example.address.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.address.type.ReferenceType;
import com.example.center.factory.CenterAddressEntityFactory;
import com.example.company.factory.CompanyAddressEntityFactory;

@Component
public class AddressEntityFactoryStrategy {
    @Autowired
    private CompanyAddressEntityFactory companyAddressEntityFactory;
    @Autowired
    private CenterAddressEntityFactory centerAddressEntityFactory;

    public AddressEntityFactory get(ReferenceType type) {
        return switch (type) {
            case COMPANY -> companyAddressEntityFactory;
            case CENTER -> centerAddressEntityFactory;
        };
    }
}
