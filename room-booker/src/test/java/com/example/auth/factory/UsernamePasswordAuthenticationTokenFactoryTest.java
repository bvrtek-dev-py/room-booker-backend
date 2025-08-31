package com.example.auth.factory;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.example.auth.dto.JwtPayload;

class UsernamePasswordAuthenticationTokenFactoryTest {
    @Test
    void make_shouldReturnTokenWithGivenPayloadAndCredentials() {
        // given
        UsernamePasswordAuthenticationTokenFactory factory = new UsernamePasswordAuthenticationTokenFactory();
        JwtPayload payload = new JwtPayload(42L, "user@example.com");
        Optional<Object> credentials = Optional.of("secret");

        // when
        UsernamePasswordAuthenticationToken token = factory.make(payload, credentials);

        // then
        assertNotNull(token);
        assertEquals(payload, token.getPrincipal());
        assertEquals(credentials, token.getCredentials());
        assertTrue(token.getAuthorities().isEmpty());
    }
}
