package com.example.apartment.mapper;

import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import com.example.apartment.dto.response.ApartmentResponse;
import com.example.apartment.entity.ApartmentEntity;
import com.example.center.mapper.CenterResponseMapperFacade;

@Component
@RequiredArgsConstructor
public class ApartmentResponseMapperFacade {
    private final ApartmentResponseMapper apartmentResponseMapper;

    private final CenterResponseMapperFacade centerResponseMapperFacade;

    public ApartmentResponse map(@NotNull ApartmentEntity apartment) {
        return apartmentResponseMapper.map(apartment, centerResponseMapperFacade.map(apartment.getCenter(), null));
    }
}
