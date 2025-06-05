package com.example.auth.factory;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.example.auth.dto.TokenResponse;

@Component
public class TokenResponseFactory {
    public TokenResponse create(String accessToken, String refreshToken, String tokenType, Date expiredAt) {
        return new TokenResponse(tokenType, expiredAt, refreshToken, accessToken);
    }
}
