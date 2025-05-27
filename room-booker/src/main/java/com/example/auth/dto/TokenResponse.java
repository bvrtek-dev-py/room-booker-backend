package com.example.auth.dto;

import java.util.Date;

public class TokenResponse {
    private final String tokenType;
    private final Date expiredAt;
    private final String refreshToken;
    private final String accessToken;

    public TokenResponse(String tokenType, Date expiredAt, String refreshToken, String accessToken) {
        this.tokenType = tokenType;
        this.expiredAt = expiredAt;
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public Date getExpiredAt() {
        return expiredAt;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
