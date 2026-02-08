package com.example.center.mapper;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import com.example.address.entity.AddressEntity;
import com.example.address.mapper.AddressResponseMapper;
import com.example.center.dto.response.CenterResponse;
import com.example.center.entity.CenterEntity;
import com.example.company.mapper.CompanyResponseMapperFacade;

@Component
@RequiredArgsConstructor
public class CenterResponseMapperFacade {
    private final CenterResponseMapper centerResponseMapper;

    private final CompanyResponseMapperFacade companyResponseMapperFacade;

    private final AddressResponseMapper addressResponseMapper;

    public CenterResponse map(@NotNull CenterEntity entity, @Nullable AddressEntity address) {
        return centerResponseMapper.map(
            entity,
            companyResponseMapperFacade.map(entity.getCompany(), null),
            address != null ? addressResponseMapper.map(address) : null
        );
    }
}
