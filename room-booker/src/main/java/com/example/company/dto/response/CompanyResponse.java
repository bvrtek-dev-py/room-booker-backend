package com.example.company.dto.response;

import com.example.user.dto.response.UserResponse;

public class CompanyResponse {
    private Long id;
    private String name;
    private UserResponse user;

    public CompanyResponse(Long id, String name, UserResponse user) {
        this.id = id;
        this.name = name;
        this.user = user;
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
}
