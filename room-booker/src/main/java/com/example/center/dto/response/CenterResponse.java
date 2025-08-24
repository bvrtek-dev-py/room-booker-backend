package com.example.center.dto.response;

import com.example.address.dto.AddressResponse;
import com.example.company.dto.response.CompanyResponse;

public class CenterResponse {
    private Long id;
    private String name;
    private String description;
    private AddressResponse address;
    private CompanyResponse company;

    public CenterResponse(Long id, String name, String description, AddressResponse address, CompanyResponse company) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.company = company;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public AddressResponse getAddress() {
        return address;
    }

    public void setAddress(AddressResponse address) {
        this.address = address;
    }

    public CompanyResponse getCompany() {
        return company;
    }

    public void setCompany(CompanyResponse company) {
        this.company = company;
    }
}