package com.example.center.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.center.dto.request.CenterCreateRequest;
import com.example.center.dto.request.CenterUpdateRequest;
import com.example.center.entity.CenterEntity;

@Mapper(componentModel = "spring")
public interface CenterEntityMapper {
    @Mapping(target = "id", ignore = true)
    CenterEntity map(CenterCreateRequest request);
    @Mapping(target = "id", source = "id")
    CenterEntity map(CenterUpdateRequest request, Long id);
}
