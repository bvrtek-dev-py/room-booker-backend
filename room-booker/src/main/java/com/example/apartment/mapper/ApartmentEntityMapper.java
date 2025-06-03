package com.example.apartment.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.apartment.dto.request.ApartmentCreateRequest;
import com.example.apartment.entity.ApartmentEntity;
import com.example.center.entity.CenterEntity;

@Mapper(componentModel = "spring")
public interface ApartmentEntityMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "request.name")
    @Mapping(target = "numberOfPeople", source = "request.numberOfPeople")
    @Mapping(target = "description", source = "request.description")
    @Mapping(target = "pricePerNight", source = "request.pricePerNight")
    @Mapping(target = "amount", source = "request.amount")
    @Mapping(target = "facilities", source = "request.facilities")
    @Mapping(target = "center", source = "center")
    ApartmentEntity map(ApartmentCreateRequest request, CenterEntity center);
}
