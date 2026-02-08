package com.example.auth.factory;

import java.util.ArrayList;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import com.example.auth.dto.JwtPayload;

@Component
public class UsernamePasswordAuthenticationTokenFactory {
    public UsernamePasswordAuthenticationToken make(@NotNull JwtPayload jwtPayload, @Nullable Object credentials) {
        return new UsernamePasswordAuthenticationToken(jwtPayload, credentials, new ArrayList<>());
    }
}
