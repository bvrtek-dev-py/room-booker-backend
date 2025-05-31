package com.example.company.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.company.dto.response.CompanyResponse;
import com.example.company.entity.CompanyEntity;
import com.example.user.dto.response.UserResponse;

@Mapper(componentModel = "spring")
public interface CompanyResponseMapper {
    @Mapping(target = "id", source = "request.id")
    @Mapping(target = "user", source = "user")
    CompanyResponse map(CompanyEntity request, UserResponse user);
}