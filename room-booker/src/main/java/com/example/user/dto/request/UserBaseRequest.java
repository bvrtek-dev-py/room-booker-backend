package com.example.user.dto.request;

import jakarta.validation.constraints.NotBlank;

public class UserBaseRequest {
    @NotBlank(message = "Username is required")
    protected String username;

    public String getUsername() {
        return username;
    }
}
