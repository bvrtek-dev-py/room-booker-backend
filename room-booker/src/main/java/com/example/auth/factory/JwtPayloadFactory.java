package com.example.auth.factory;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

import com.example.auth.dto.JwtPayload;

@Component
public class JwtPayloadFactory {
    public JwtPayload make(@NotNull Long id, @NotBlank String email) {
        return new JwtPayload(id, email);
    }
}
