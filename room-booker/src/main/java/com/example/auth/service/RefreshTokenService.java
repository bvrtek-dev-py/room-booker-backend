package com.example.auth.service;

import org.springframework.stereotype.Service;

@Service
public class RefreshTokenService extends TokenService {
    private final String REFRESH_TOKEN_SECRET_KEY = "3q2+7w==3q2+7w==3q2+7w==3q2+7w=="; // @TODO environment variable
    private final long EXPIRATION_TIME_MILLISECONDS = 7 * 24 * 60 * 60 * 1000; // 7 dni

    @Override
    protected byte[] getSecretKeyBytes() {
        return REFRESH_TOKEN_SECRET_KEY.getBytes();
    }

    @Override
    protected long getExpirationTime() {
        return EXPIRATION_TIME_MILLISECONDS;
    }
}
