package com.example.auth.service;

import java.util.Date;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public abstract class TokenService {
    protected abstract byte[] getSecretKeyBytes();
    protected abstract long getExpirationTime();

    public String generate(String email, Long id, long currentTimeMillis) {
        SecretKey key = Keys.hmacShaKeyFor(getSecretKeyBytes());

        return Jwts.builder()
                .setSubject(email)
                .claim("id", id)
                .setIssuedAt(new Date())
                .setExpiration(new Date(currentTimeMillis + getExpirationTime()))
                .signWith(key)
                .compact();
    }
}