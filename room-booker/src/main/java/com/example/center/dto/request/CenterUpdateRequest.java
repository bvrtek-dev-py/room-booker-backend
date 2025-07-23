package com.example.center.dto.request;

import com.example.address.dto.AddressCreateRequest;

public record CenterUpdateRequest(String name, String description, AddressCreateRequest address) {}
