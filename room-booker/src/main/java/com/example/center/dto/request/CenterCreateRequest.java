package com.example.center.dto.request;

import com.example.address.dto.AddressCreateRequest;

public class CenterCreateRequest {
    private String name;
    private String description;
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
