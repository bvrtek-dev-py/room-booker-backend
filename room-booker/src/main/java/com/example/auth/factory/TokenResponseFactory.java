package com.example.auth.factory;

import java.util.Date;

import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

import com.example.auth.dto.TokenResponse;

@Component
public class TokenResponseFactory {
    public TokenResponse create(@NotNull String accessToken, @NotNull String refreshToken, @NotNull String tokenType, @NotNull Date expiredAt) {
        return new TokenResponse(tokenType, expiredAt, refreshToken, accessToken);
    }
}
