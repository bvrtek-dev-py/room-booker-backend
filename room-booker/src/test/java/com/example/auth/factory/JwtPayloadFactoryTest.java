package com.example.auth.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

import com.example.auth.dto.JwtPayload;

class JwtPayloadFactoryTest {
    @Test
    void make_shouldReturnJwtPayloadWithGivenIdAndEmail() {
        // given
        JwtPayloadFactory factory = new JwtPayloadFactory();
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
