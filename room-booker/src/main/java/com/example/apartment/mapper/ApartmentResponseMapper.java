package com.example.apartment.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.apartment.dto.response.ApartmentResponse;
import com.example.apartment.entity.ApartmentEntity;
import com.example.center.dto.response.CenterResponse;

@Mapper(componentModel = "spring")
public interface ApartmentResponseMapper {
    @Mapping(target = "id", source = "apartment.id")
    @Mapping(target = "name", source = "apartment.name")
    @Mapping(target = "numberOfPeople", source = "apartment.numberOfPeople")
    @Mapping(target = "description", source = "apartment.description")
    @Mapping(target = "center", source = "center")
    ApartmentResponse map(ApartmentEntity apartment, CenterResponse center);
}
