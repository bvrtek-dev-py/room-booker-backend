package com.example.center.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.address.dto.AddressResponse;
import com.example.center.dto.response.CenterResponse;
import com.example.center.entity.CenterEntity;
import com.example.company.dto.response.CompanyResponse;

@Mapper(componentModel = "spring")
public interface CenterResponseMapper {
    @Mapping(target = "company", source = "company")
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "name", source = "entity.name")
    @Mapping(target = "description", source = "entity.description")
    @Mapping(target = "address", source = "address")
    CenterResponse map(CenterEntity entity, CompanyResponse company, AddressResponse address);
}
