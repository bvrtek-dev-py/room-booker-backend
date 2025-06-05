package com.example.center.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.center.dto.response.CenterResponse;
import com.example.center.entity.CenterEntity;
import com.example.company.mapper.CompanyResponseMapperFacade;

@Component
public class CenterResponseMapperFacade {
    @Autowired
    private CenterResponseMapper centerResponseMapper;

    @Autowired
    private CompanyResponseMapperFacade companyResponseMapperFacade;

    public CenterResponse map(CenterEntity entity) {
        return centerResponseMapper.map(entity, companyResponseMapperFacade.map(entity.getCompany()));
    }
}
