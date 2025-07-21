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
}
