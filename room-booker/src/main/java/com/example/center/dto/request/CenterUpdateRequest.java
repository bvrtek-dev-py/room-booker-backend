package com.example.center.dto.request;

import com.example.address.dto.AddressCreateRequest;

public class CenterUpdateRequest {
    private String name;
    private String description;
    private AddressCreateRequest address;

    public CenterUpdateRequest(String name, String description, AddressCreateRequest address) {
        this.name = name;
        this.description = description;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AddressCreateRequest getAddress() {
        return address;
    }

    public void setAddress(AddressCreateRequest address) {
        this.address = address;
    }
}
