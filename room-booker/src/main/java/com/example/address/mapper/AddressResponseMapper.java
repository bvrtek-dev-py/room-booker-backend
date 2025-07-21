package com.example.address.mapper;

import org.mapstruct.Mapper;

import com.example.address.dto.AddressResponse;
import com.example.address.entity.AddressEntity;

@Mapper(componentModel = "spring")
public interface AddressResponseMapper {
    AddressResponse map(AddressEntity entity);
}
