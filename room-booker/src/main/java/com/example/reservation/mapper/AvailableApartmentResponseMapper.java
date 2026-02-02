package com.example.reservation.mapper;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.example.apartment.entity.ApartmentEntity;
import com.example.reservation.dto.response.AvailableApartmentResponse;

@Component
public class AvailableApartmentResponseMapper {
    public AvailableApartmentResponse map(
            ApartmentEntity apartment,
            LocalDate checkInDate,
            LocalDate checkOutDate,
            int totalRooms,
            int reservedRooms,
            int availableRooms
    ) {
        long numberOfNights = java.time.temporal.ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        double totalPrice = numberOfNights * apartment.getPricePerNight();

        return new AvailableApartmentResponse(
                apartment.getId(),
                apartment.getName(),
                apartment.getNumberOfPeople(),
                apartment.getDescription(),
                apartment.getPricePerNight(),
                apartment.getFacilities(),
                checkInDate,
                checkOutDate,
                totalPrice,
                totalRooms,
                reservedRooms,
                availableRooms);
    }
}
