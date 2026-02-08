package com.example.auth.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RefreshTokenServiceTest {
    @InjectMocks
    private RefreshTokenService service;

    @Test
    void generate_shouldCreateValidRefreshToken() {
        // given
        String email = "user@example.com";
        Long id = 123L;
        long now = System.currentTimeMillis();
        // when
        String token = service.generate(email, id, now);
        // then
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }
}
