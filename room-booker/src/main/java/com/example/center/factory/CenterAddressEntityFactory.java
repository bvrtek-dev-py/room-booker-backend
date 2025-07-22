package com.example.center.factory;

import org.springframework.stereotype.Component;

import com.example.address.dto.AddressCreateRequest;
import com.example.address.entity.AddressEntity;
import com.example.address.factory.AddressEntityFactory;
import com.example.center.entity.CenterAddressEntity;

@Component
public class CenterAddressEntityFactory implements AddressEntityFactory {
    @Override
    public AddressEntity make(AddressCreateRequest request, Long objectId) {
        return new CenterAddressEntity(
            request.getStreet(),
            request.getCity(),
            request.getState(),
            request.getZipCode(),
            request.getCountry(),
            objectId
        );
    }
}