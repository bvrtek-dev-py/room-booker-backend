package com.example.reservation.mapper;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Component;

import com.example.apartment.entity.ApartmentEntity;
import com.example.reservation.dto.request.ReservationCreateRequest;
import com.example.reservation.entity.ReservationEntity;
import com.example.user.entity.UserEntity;

@Component
public class ReservationEntityMapper {
    public ReservationEntity map(ReservationCreateRequest request, UserEntity user, ApartmentEntity apartment) {
        LocalDate checkInDate = request.getCheckInDate();
        LocalDate checkOutDate = request.getCheckOutDate();
        long numberOfNights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        double totalPrice = numberOfNights * apartment.getPricePerNight();

        return new ReservationEntity(
                null, user, apartment, checkInDate, checkOutDate, request.getNumberOfGuests(), totalPrice);
    }
}
