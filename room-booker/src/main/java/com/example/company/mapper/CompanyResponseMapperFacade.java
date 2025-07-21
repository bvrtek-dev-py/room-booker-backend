package com.example.company.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.example.address.entity.AddressEntity;
import com.example.address.mapper.AddressResponseMapper;
import com.example.company.dto.response.CompanyResponse;
import com.example.company.entity.CompanyEntity;
import com.example.user.mapper.UserResponseMapper;

@Component
public class CompanyResponseMapperFacade {
    @Autowired
    private CompanyResponseMapper companyResponseMapper;

    @Autowired
    private UserResponseMapper userResponseMapper;

    @Autowired
    private AddressResponseMapper addressResponseMapper;

    public CompanyResponse map(CompanyEntity entity, @Nullable AddressEntity address) {
        return companyResponseMapper.map(
            entity,
            userResponseMapper.map(entity.getUser()),
            address != null ? addressResponseMapper.map(address) : null
        );
    }
}
