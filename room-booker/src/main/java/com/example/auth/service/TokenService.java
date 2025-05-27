package com.example.auth.service;

import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public abstract class TokenService {
    protected abstract byte[] getSecretKeyBytes();
    protected abstract long getExpirationTime();

    public String generate(String email, Long id, long currentTimeMillis) {
        return Jwts.builder()
                .setSubject(email)
                .claim("id", id)
                .setIssuedAt(new Date())
                .setExpiration(new Date(currentTimeMillis + getExpirationTime()))
                .signWith(getSecretKey())
                .compact();
    }

    protected Key getSecretKey() {
        byte[] keyBytes = getSecretKeyBytes(); // Pobierz klucz jako tablicę bajtów
        return Keys.hmacShaKeyFor(keyBytes); // Utwórz obiekt Key
    }
}