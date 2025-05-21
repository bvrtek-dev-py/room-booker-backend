package com.example.user.dto.request;

import jakarta.validation.constraints.NotBlank;

public class ChangePasswordRequest {
    @NotBlank(message = "Old password is required")
    private String password;

    @NotBlank(message = "New password is required")
    private String newPassword;

    @NotBlank(message = "Password confirmation is required")
    private String passwordConfirmation;

    public String getPassword() {
        return password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }
}