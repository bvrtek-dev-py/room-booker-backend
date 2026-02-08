package com.example.company.mapper;

import jakarta.validation.constraints.NotNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import com.example.address.entity.AddressEntity;
import com.example.address.mapper.AddressResponseMapper;
import com.example.company.dto.response.CompanyResponse;
import com.example.company.entity.CompanyEntity;
import com.example.user.mapper.UserResponseMapper;

@Component
@RequiredArgsConstructor
public class CompanyResponseMapperFacade {
    private final CompanyResponseMapper companyResponseMapper;

    private final UserResponseMapper userResponseMapper;

    private final AddressResponseMapper addressResponseMapper;

    public CompanyResponse map(@NotNull CompanyEntity entity, @Nullable AddressEntity address) {
        return companyResponseMapper.map(
            entity,
            userResponseMapper.map(entity.getUser()),
            address != null ? addressResponseMapper.map(address) : null
        );
    }
}
