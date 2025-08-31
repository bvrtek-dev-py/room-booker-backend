package com.example.auth.factory;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.auth.dto.TokenResponse;


@ExtendWith(MockitoExtension.class)
class TokenResponseFactoryTest {
    @InjectMocks
    private TokenResponseFactory factory;

    @Test
    void create_shouldReturnTokenResponseWithGivenArguments() {
        // given
        String accessToken = "access";
        String refreshToken = "refresh";
        String tokenType = "Bearer";
        Date expiredAt = new Date();

        // when
        TokenResponse response = factory.create(accessToken, refreshToken, tokenType, expiredAt);

        // then
        assertNotNull(response);
        assertEquals(tokenType, response.getTokenType());
        assertEquals(expiredAt, response.getExpiredAt());
        assertEquals(refreshToken, response.getRefreshToken());
        assertEquals(accessToken, response.getAccessToken());
    }
}
