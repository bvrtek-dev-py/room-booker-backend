package com.example.apartment.dto.request;

import java.util.List;

import com.example.apartment.type.Facility;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ApartmentBaseRequest {
    @NotBlank(message = "Apartment name is required")
    private String name;

    @NotNull(message = "Number of people is required")
    private int numberOfPeople;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Price per night is required")
    private double pricePerNight;

    @NotNull(message = "Amount is required")
    private int amount;

    @NotNull(message = "Facilities are required")
    private List<Facility> facilities;

    public String getName() {
        return name;
    }

    public Integer getAmount() {
        return amount;
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
}
