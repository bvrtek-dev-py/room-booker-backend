package com.example.center.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import com.example.address.constant.AddressConstant;
import com.example.address.entity.AddressEntity;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue(AddressConstant.CENTER)
@NoArgsConstructor
public class CenterAddressEntity extends AddressEntity {
    public CenterAddressEntity(
            String street, String city, String state, String zipCode, String country, Long objectId) {
        super(null, street, city, state, zipCode, country, objectId);
    }

    @Override
    public AddressEntity with(String street, String city, String state, String zipCode, String country) {
        return new CenterAddressEntity(
            street != null ? street : getStreet(),
            city != null ? city : getCity(),
            state != null ? state : getState(),
            zipCode != null ? zipCode : getZipCode(),
            country != null ? country : getCountry(),
            getObjectId()
        );
    }
}
