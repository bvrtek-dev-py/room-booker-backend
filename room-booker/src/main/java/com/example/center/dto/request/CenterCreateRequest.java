package com.example.center.dto.request;

import com.example.address.dto.AddressCreateRequest;

import jakarta.validation.constraints.NotNull;

public class CenterCreateRequest {
    @NotNull(message = "Name is required")
    private String name;
    @NotNull(message = "Description is required")
    private String description;
    @NotNull(message = "Address is required")
    private AddressCreateRequest address;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public AddressCreateRequest getAddress() {
        return address;
    }
}
