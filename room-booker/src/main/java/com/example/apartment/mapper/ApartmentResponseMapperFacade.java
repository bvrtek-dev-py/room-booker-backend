package com.example.apartment.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.apartment.dto.response.ApartmentResponse;
import com.example.apartment.entity.ApartmentEntity;
import com.example.center.mapper.CenterResponseMapperFacade;

@Component
public class ApartmentResponseMapperFacade {
    @Autowired
    private ApartmentResponseMapper apartmentResponseMapper;

    @Autowired
    private CenterResponseMapperFacade centerResponseMapperFacade;

    public ApartmentResponse map(ApartmentEntity apartment) {
        return apartmentResponseMapper.map(apartment, centerResponseMapperFacade.map(apartment.getCenter()));
    }
}
