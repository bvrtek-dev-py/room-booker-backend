package com.example.center.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.address.entity.AddressEntity;
import com.example.address.mapper.AddressResponseMapper;
import com.example.center.dto.response.CenterResponse;
import com.example.center.entity.CenterEntity;
import com.example.center.repository.CenterAddressRepository;
import com.example.company.mapper.CompanyResponseMapperFacade;

@Component
public class CenterResponseMapperFacade {
    @Autowired
    private CenterResponseMapper centerResponseMapper;

    @Autowired
    private CompanyResponseMapperFacade companyResponseMapperFacade;

    @Autowired
    private AddressResponseMapper addressResponseMapper;

    @Autowired
    private CenterAddressRepository centerAddressRepository;

    public CenterResponse map(CenterEntity entity) {
        AddressEntity address = centerAddressRepository.findByObjectId(entity.getId()).orElse(null);
        return centerResponseMapper.map(
            entity,
            companyResponseMapperFacade.map(entity.getCompany(), null),
            address != null ? addressResponseMapper.map(address) : null
        );
    }
}
