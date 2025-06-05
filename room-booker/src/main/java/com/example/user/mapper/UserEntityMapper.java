package com.example.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.user.dto.request.UserCreateRequest;
import com.example.user.dto.request.UserUpdateRequest;
import com.example.user.entity.UserEntity;
import com.example.user.role.UserRole;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", source = "role")
    UserEntity map(UserCreateRequest request, UserRole role);

    @Mapping(target = "id", source = "userEntity.id")
    @Mapping(target = "username", source = "request.username")
    @Mapping(target = "email", source = "userEntity.email")
    @Mapping(target = "role", source = "userEntity.role")
    @Mapping(target = "password", source = "userEntity.password")
    UserEntity map(UserUpdateRequest request, UserEntity userEntity);
}
