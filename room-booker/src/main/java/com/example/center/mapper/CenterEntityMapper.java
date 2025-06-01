package com.example.center.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.center.dto.request.CenterCreateRequest;
import com.example.center.entity.CenterEntity;
import com.example.company.entity.CompanyEntity;

@Mapper(componentModel = "spring")
public interface CenterEntityMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "request.name")
    @Mapping(target = "company", source= "company")
    CenterEntity map(CenterCreateRequest request, CompanyEntity company);
}
