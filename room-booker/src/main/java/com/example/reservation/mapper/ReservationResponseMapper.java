package com.example.reservation.mapper;

import org.springframework.stereotype.Component;

import com.example.reservation.dto.response.ReservationResponse;
import com.example.reservation.entity.ReservationEntity;

@Component
public class ReservationResponseMapper {
    public ReservationResponse map(ReservationEntity reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getUser().getId(),
                reservation.getApartment().getId(),
                reservation.getApartment().getName(),
                reservation.getCheckInDate(),
                reservation.getCheckOutDate(),
                reservation.getNumberOfGuests(),
                reservation.getTotalPrice()
        );
    }
}
