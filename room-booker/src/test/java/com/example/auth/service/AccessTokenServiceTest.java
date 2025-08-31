package com.example.auth.service;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.auth.dto.JwtPayload;
import com.example.auth.factory.JwtPayloadFactory;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;

@ExtendWith(MockitoExtension.class)
class AccessTokenServiceTest {

    @Mock
    private JwtPayloadFactory jwtPayloadFactory;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private AccessTokenService accessTokenService;

    private final String secretKey = "3q2+7w==3q2+7w==3q2+7w==3q2+7w==";

    @Test
    void extractTokenFromHeader_shouldReturnToken() {
        // given
        when(request.getHeader("Authorization")).thenReturn("Bearer test.token.value");

        // when
        String token = accessTokenService.extractTokenFromHeader(request);

        // then
        assertEquals("test.token.value", token);
    }

    @Test
    void extractTokenFromHeader_shouldReturnNullIfNoBearer() {
        // given
        when(request.getHeader("Authorization")).thenReturn("Basic something");

        // when
        String token = accessTokenService.extractTokenFromHeader(request);

        // then
        assertNull(token);
    }

    @Test
    void extractPayload_shouldReturnJwtPayload() {
        // given
        String email = "user@example.com";
        Long id = 123L;
        Date exp = new Date(System.currentTimeMillis() + 10000);
        String token = Jwts.builder()
                .setSubject(email)
                .claim("id", id)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
                .compact();

        JwtPayload expectedPayload = new JwtPayload(id, email);
        when(jwtPayloadFactory.make(id, email)).thenReturn(expectedPayload);

        // when
        JwtPayload payload = accessTokenService.extractPayload(token);

        // then
        assertEquals(expectedPayload, payload);
    }

    @Test
    void isTokenValid_shouldReturnTrueForValidToken() {
        // given
        String email = "user@example.com";
        Long id = 123L;
        Date exp = new Date(System.currentTimeMillis() + 10000);
        String token = Jwts.builder()
                .setSubject(email)
                .claim("id", id)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
                .compact();

        when(jwtPayloadFactory.make(id, email)).thenReturn(new JwtPayload(id, email));

        // when
        boolean result = accessTokenService.isTokenValid(token);

        // then
        assertTrue(result);
    }

    @Test
    void getExpirationDate_shouldReturnExpiration() {
        // given
        String email = "user@example.com";
        Long id = 123L;
        Date exp = new Date(System.currentTimeMillis() + 10000);
        String token = Jwts.builder()
                .setSubject(email)
                .claim("id", id)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
                .compact();

        // when
        Date result = accessTokenService.getExpirationDate(token);

        // then
        assertEquals(exp.getTime() / 1000, result.getTime() / 1000);
    }

    @Test
    void generate_shouldReturnNonEmptyToken() {
        // given
        String email = "user@example.com";
        Long id = 123L;
        long now = System.currentTimeMillis();

        // when
        String token = accessTokenService.generate(email, id, now);

        // then
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }
}
