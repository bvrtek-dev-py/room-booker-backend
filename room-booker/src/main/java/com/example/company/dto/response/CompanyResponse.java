package com.example.company.dto.response;

import com.example.address.dto.AddressResponse;
import com.example.user.dto.response.UserResponse;

public class CompanyResponse {
    private Long id;
    private String name;
    private UserResponse user;
    private AddressResponse address;

    public CompanyResponse(Long id, String name, UserResponse user, AddressResponse address) {
        this.id = id;
        this.name = name;
        this.user = user;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public UserResponse getUser() {
        return user;
    }

    public AddressResponse getAddress() {
        return address;
    }
}
