package com.example.reservation.dto.request;

import java.time.LocalDate;

public class ReservationCreateRequest {
    private Long apartmentId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int numberOfGuests;

    protected ReservationCreateRequest() {}

    public ReservationCreateRequest(
            Long apartmentId, LocalDate checkInDate, LocalDate checkOutDate, int numberOfGuests) {
        this.apartmentId = apartmentId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.numberOfGuests = numberOfGuests;
    }

    public Long getApartmentId() {
        return apartmentId;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }
}
