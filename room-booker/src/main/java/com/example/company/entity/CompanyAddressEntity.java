package com.example.company.entity;

import com.example.address.constant.AddressConstant;
import com.example.address.entity.AddressEntity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue(AddressConstant.COMPANY)
public class CompanyAddressEntity extends AddressEntity {
    protected CompanyAddressEntity() {
        // Required by JPA
    }

    public CompanyAddressEntity(String street, String city, String state, String zipCode, String country, Long objectId) {
        super(null, street, city, state, zipCode, country, objectId);
    }

    @Override
    public AddressEntity with(String street, String city, String state, String zipCode, String country) {
        return new CompanyAddressEntity(
            street != null ? street : getStreet(),
            city != null ? city : getCity(),
            state != null ? state : getState(),
            zipCode != null ? zipCode : getZipCode(),
            country != null ? country : getCountry(),
            getObjectId()
        );
    }
}
