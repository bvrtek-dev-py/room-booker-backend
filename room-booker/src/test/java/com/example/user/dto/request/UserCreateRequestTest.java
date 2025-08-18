package com.example.user.dto.request;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import org.junit.jupiter.api.Test;

class UserCreateRequestTest {

    @Test
    void testWithMethod_AllFieldsChanged() {
        UserCreateRequest original = new UserCreateRequest("user1", "pass1", "email1@example.com");

        UserCreateRequest updated = original.with(null, "email2@example.com", "user2");

        assertNotSame(original, updated);

        assertEquals("pass1", updated.getPassword());
        assertEquals("email2@example.com", updated.getEmail());
        assertEquals("user2", updated.getUsername());
    }
}
