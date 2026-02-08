package com.example.apartment.dto.request;

import com.example.apartment.type.Facility;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ApartmentCreateRequest {
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
