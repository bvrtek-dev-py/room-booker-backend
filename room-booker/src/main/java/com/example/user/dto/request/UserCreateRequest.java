package com.example.user.dto.request;

import java.util.Optional;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserCreateRequest extends UserBaseRequest {
    @NotBlank(message = "Password is required")
    protected String password;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    protected String email;

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public UserCreateRequest with(Optional<String> newPassword, Optional<String> newEmail, Optional<String> username) {
        UserCreateRequest newRequest = new UserCreateRequest();

        newRequest.password = newPassword.orElse(this.password);
        newRequest.email = newEmail.orElse(this.email);
        newRequest.username = username.orElse(this.username);

        return newRequest;
    }
}
