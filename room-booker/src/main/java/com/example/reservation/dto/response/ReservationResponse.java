package com.example.reservation.dto.response;

import java.time.LocalDate;

public class ReservationResponse {
    private Long id;
    private Long userId;
    private Long apartmentId;
    private String apartmentName;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int numberOfGuests;
    private double totalPrice;

    protected ReservationResponse() {}

    public ReservationResponse(
            Long id,
            Long userId,
            Long apartmentId,
            String apartmentName,
            LocalDate checkInDate,
            LocalDate checkOutDate,
            int numberOfGuests,
            double totalPrice) {
        this.id = id;
        this.userId = userId;
        this.apartmentId = apartmentId;
        this.apartmentName = apartmentName;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.numberOfGuests = numberOfGuests;
        this.totalPrice = totalPrice;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getApartmentId() {
        return apartmentId;
    }

    public String getApartmentName() {
        return apartmentName;
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

    public double getTotalPrice() {
        return totalPrice;
    }
}
