package com.example.apartment.dto.response;

import java.util.List;

import com.example.apartment.type.Facility;
import com.example.center.dto.response.CenterResponse;

public record ApartmentResponse(
        Long id,
        String name,
        Integer numberOfPeople,
        String description,
        Double pricePerNight,
        Integer amount,
        List<Facility> facilities,
        CenterResponse center) {}
