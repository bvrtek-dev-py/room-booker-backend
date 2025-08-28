package com.example.company.dto.request;

import com.example.address.dto.AddressCreateRequest;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CompanyCreateRequest {

    @NotNull
    @Size(min = 1, max = 255)
    private String name;

    private AddressCreateRequest address;

    public CompanyCreateRequest(String name, AddressCreateRequest address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public AddressCreateRequest getAddress() {
        return address;
    }
}
