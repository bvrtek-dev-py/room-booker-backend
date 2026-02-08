package com.example.auth.dto;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse {
    @NotBlank
    private String tokenType;
    @NotNull
    private Date expiredAt;
    @NotBlank
    private String refreshToken;
    @NotBlank
    private String accessToken;
}
