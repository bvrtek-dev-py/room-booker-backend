package com.example.company.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.company.dto.response.CompanyResponse;
import com.example.company.entity.CompanyEntity;
import com.example.user.mapper.UserResponseMapper;

@Component
public class CompanyResponseMapperFacade {
    @Autowired
    private CompanyResponseMapper companyResponseMapper;

    @Autowired
    private UserResponseMapper userResponseMapper;

    public CompanyResponse map(CompanyEntity entity) {
        return companyResponseMapper.map(entity, userResponseMapper.map(entity.getUser()));
    }
}
