package com.example.auth.service;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.auth.dto.JwtPayload;
import com.example.auth.dto.TokenResponse;
import com.example.auth.factory.TokenResponseFactory;

@Service
@RequiredArgsConstructor
public class LoginService {
    private static final String BEARER = "Bearer";

    private final AuthenticationManager authenticationManager;

    private final AccessTokenService accessTokenService;

    private final RefreshTokenService refreshTokenService;

    private final TokenResponseFactory tokenResponseFactory;

    public TokenResponse login(@NotNull String email, @NotNull String password) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        JwtPayload userDetails = (JwtPayload) authentication.getPrincipal();
        long currentTimeMillis = System.currentTimeMillis();

        String accessToken =
                accessTokenService.generate(userDetails.getEmail(), userDetails.getId(), currentTimeMillis);
        String refreshToken =
                refreshTokenService.generate(userDetails.getEmail(), userDetails.getId(), currentTimeMillis);

        return tokenResponseFactory.create(
            accessToken, refreshToken, BEARER, accessTokenService.getExpirationDate(accessToken)
        );
    }
}
