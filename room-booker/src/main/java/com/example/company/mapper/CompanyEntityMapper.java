package com.example.company.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.company.dto.CompanyCreateRequest;
import com.example.company.dto.CompanyUpdateRequest;
import com.example.company.entity.CompanyEntity;

@Mapper(componentModel = "spring")
public interface CompanyEntityMapper {
    @Mapping(target = "id", ignore = true)
    CompanyEntity toEntity(CompanyCreateRequest request);

    @Mapping(target = "id", source = "id")
    CompanyEntity toEntity(CompanyUpdateRequest request, Long id);
}