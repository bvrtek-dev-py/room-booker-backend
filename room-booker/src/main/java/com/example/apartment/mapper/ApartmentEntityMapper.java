package com.example.apartment.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.apartment.dto.request.ApartmentCreateRequest;
import com.example.apartment.dto.request.ApartmentUpdateRequest;
import com.example.apartment.entity.ApartmentEntity;

@Mapper(componentModel = "spring")
public interface ApartmentEntityMapper {
    @Mapping(target = "id", ignore = true)
    ApartmentEntity map(ApartmentCreateRequest request);
    @Mapping(target = "id", source = "id")
    ApartmentEntity map(ApartmentUpdateRequest request, Long id);
}
