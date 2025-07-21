package com.example.address.factory;

import com.example.address.dto.AddressCreateRequest;
import com.example.address.entity.AddressEntity;

public interface AddressEntityFactory {
    public AddressEntity make(AddressCreateRequest request, Long objectId);
}
