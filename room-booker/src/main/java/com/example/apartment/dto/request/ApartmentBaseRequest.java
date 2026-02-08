package com.example.apartment.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.example.apartment.type.Facility;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
}
