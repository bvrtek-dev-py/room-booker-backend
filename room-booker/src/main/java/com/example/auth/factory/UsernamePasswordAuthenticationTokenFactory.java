package com.example.auth.factory;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import com.example.auth.dto.JwtPayload;

@Component
public class UsernamePasswordAuthenticationTokenFactory {
    public UsernamePasswordAuthenticationToken make(JwtPayload jwtPayload, Optional<Object> credentials) {
        return new UsernamePasswordAuthenticationToken(jwtPayload, credentials, new ArrayList<>());
    }
}
