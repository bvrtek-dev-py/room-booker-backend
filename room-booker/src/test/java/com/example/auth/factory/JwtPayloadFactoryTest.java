package com.example.auth.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.auth.dto.JwtPayload;

@ExtendWith(MockitoExtension.class)
class JwtPayloadFactoryTest {
    @InjectMocks
    private JwtPayloadFactory factory;

    @Test
    void make_shouldReturnJwtPayloadWithGivenIdAndEmail() {
        // given
        Long id = 123L;
        String email = "test@example.com";

        // when
        JwtPayload payload = factory.make(id, email);

        // then
        assertNotNull(payload);
        assertEquals(id, payload.getId());
        assertEquals(email, payload.getEmail());
    }
}
