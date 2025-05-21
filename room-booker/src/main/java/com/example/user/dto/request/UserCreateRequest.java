package com.example.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserCreateRequest extends UserBaseRequest {
    @NotBlank(message = "Password is required")
    protected  String password;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    protected  String email;

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}