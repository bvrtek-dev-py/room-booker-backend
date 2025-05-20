package com.example.apartment.mapper;

import org.mapstruct.Mapper;

import com.example.apartment.dto.response.ApartmentResponse;
import com.example.apartment.entity.ApartmentEntity;

@Mapper(componentModel = "spring")
public interface ApartmentResponseMapper {
    ApartmentResponse map(ApartmentEntity apartment);
}
