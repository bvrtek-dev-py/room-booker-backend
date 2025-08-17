package com.example.center.dto.response;

import com.example.address.dto.AddressResponse;
import com.example.company.dto.response.CompanyResponse;

public record CenterResponse(Long id, String name, String description, CompanyResponse company, AddressResponse address) {}
