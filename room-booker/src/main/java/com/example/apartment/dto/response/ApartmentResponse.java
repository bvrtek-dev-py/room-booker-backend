package com.example.apartment.dto.response;

import java.util.List;

import com.example.apartment.type.Facility;
import com.example.center.dto.response.CenterResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApartmentResponse {
    @NotNull
    private Long id;
    @NotBlank
    private String name;
    @NotNull
    private Integer numberOfPeople;
    @NotBlank
    private String description;
    @NotNull
    private Double pricePerNight;
    @NotNull
    private Integer amount;
    @NotNull
    private List<Facility> facilities;
    @NotNull
    private CenterResponse center;
}
