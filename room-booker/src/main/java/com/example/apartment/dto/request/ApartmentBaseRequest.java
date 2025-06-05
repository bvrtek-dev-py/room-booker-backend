package com.example.apartment.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.example.apartment.type.Facility;

public class ApartmentBaseRequest {
    @NotBlank(message = "Apartment name is required")
    protected String name;

    @NotNull(message = "Number of people is required")
    protected int numberOfPeople;

    @NotBlank(message = "Description is required")
    protected String description;

    @NotNull(message = "Price per night is required")
    protected double pricePerNight;

    @NotNull(message = "Amount is required")
    protected int amount;

    @NotNull(message = "Facilities are required")
    protected List<Facility> facilities;

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
