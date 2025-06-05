package com.example.auth.service;

import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.auth.dto.JwtPayload;
import com.example.auth.factory.JwtPayloadFactory;

@Service
public class AccessTokenService extends TokenService {
    @Autowired
    private JwtPayloadFactory jwtPayloadFactory;

    private final String SECRET_KEY = "3q2+7w==3q2+7w==3q2+7w==3q2+7w=="; // @TODO environment variable
    private final long EXPIRATION_TIME_MILLISECONDS = 15 * 60 * 1000; // 15 minut
    private final int TOKEN_START_INDEX = 7;
    private final String AUTHORIZATION = "Authorization";

    @Override
    protected byte[] getSecretKeyBytes() {
        return SECRET_KEY.getBytes();
    }

    @Override
    protected long getExpirationTime() {
        return EXPIRATION_TIME_MILLISECONDS;
    }

    public JwtPayload extractPayload(String token) {
        Claims claims = extractClaims(token);
        Long id = claims.get("id", Long.class);
        String email = claims.getSubject();

        return jwtPayloadFactory.make(id, email);
    }

    public boolean isTokenValid(String token) {
        JwtPayload payload = extractPayload(token);
        return payload != null && !isTokenExpired(token);
    }

    public String extractTokenFromHeader(HttpServletRequest request) {
        final String authHeader = request.getHeader(AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(TOKEN_START_INDEX);
        }

        return null;
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Date getExpirationDate(String token) {
        return extractClaims(token).getExpiration();
    }
}
