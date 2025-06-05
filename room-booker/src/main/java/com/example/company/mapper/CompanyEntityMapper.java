package com.example.company.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.company.dto.request.CompanyCreateRequest;
import com.example.company.dto.request.CompanyUpdateRequest;
import com.example.company.entity.CompanyEntity;
import com.example.user.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface CompanyEntityMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "user")
    CompanyEntity map(CompanyCreateRequest request, UserEntity user);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "user", source = "user")
    CompanyEntity map(CompanyUpdateRequest request, Long id, UserEntity user);
}
