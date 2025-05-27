package com.example.auth.factory;

import org.springframework.stereotype.Component;

import com.example.auth.dto.JwtPayload;

@Component
public class JwtPayloadFactory {
    public JwtPayload make(Long id, String email) {
        return new JwtPayload(id, email);
    }
}