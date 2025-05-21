package com.example.user.mapper;

import org.mapstruct.Mapper;

import com.example.user.dto.response.UserResponse;
import com.example.user.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface UserResponseMapper {

    UserResponse map(UserEntity user);
}
