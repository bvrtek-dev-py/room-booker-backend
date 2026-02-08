package com.example.address.factory;

import com.example.address.dto.AddressCreateRequest;
import com.example.address.entity.AddressEntity;
import jakarta.validation.constraints.NotNull;

public interface AddressEntityFactory {
    AddressEntity make(@NotNull AddressCreateRequest request, @NotNull Long objectId);
}
