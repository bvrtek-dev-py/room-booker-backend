package com.example.center.mapper;

import org.mapstruct.Mapper;

import com.example.center.dto.response.CenterResponse;
import com.example.center.entity.CenterEntity;

@Mapper(componentModel = "spring")
public interface CenterResponseMapper {
    CenterResponse map(CenterEntity entity);
}
