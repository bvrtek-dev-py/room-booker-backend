package com.example.auth.factory;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.example.auth.dto.JwtPayload;


@ExtendWith(MockitoExtension.class)
class UsernamePasswordAuthenticationTokenFactoryTest {
    @InjectMocks
    private UsernamePasswordAuthenticationTokenFactory factory;

    @Test
    void make_shouldReturnTokenWithGivenPayloadAndCredentials() {
        // given
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
