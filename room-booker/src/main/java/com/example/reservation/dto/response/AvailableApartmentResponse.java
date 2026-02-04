package com.example.reservation.dto.response;

import java.time.LocalDate;
import java.util.List;

import com.example.apartment.type.Facility;

public class AvailableApartmentResponse {
    private Long id;
    private String name;
    private int numberOfPeople;
    private String description;
    private double pricePerNight;
    private List<Facility> facilities;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private double totalPrice;
    private int totalRooms;
    private int reservedRooms;
    private int availableRooms;

    protected AvailableApartmentResponse() {}

    public AvailableApartmentResponse(
            Long id,
            String name,
            int numberOfPeople,
            String description,
            double pricePerNight,
            List<Facility> facilities,
            LocalDate checkInDate,
            LocalDate checkOutDate,
            double totalPrice,
            int totalRooms,
            int reservedRooms,
            int availableRooms) {
        this.id = id;
        this.name = name;
        this.numberOfPeople = numberOfPeople;
        this.description = description;
        this.pricePerNight = pricePerNight;
        this.facilities = facilities;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalPrice = totalPrice;
        this.totalRooms = totalRooms;
        this.reservedRooms = reservedRooms;
        this.availableRooms = availableRooms;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public String getDescription() {
        return description;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public List<Facility> getFacilities() {
        return facilities;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public int getTotalRooms() {
        return totalRooms;
    }

    public int getReservedRooms() {
        return reservedRooms;
    }

    public int getAvailableRooms() {
        return availableRooms;
    }
}
