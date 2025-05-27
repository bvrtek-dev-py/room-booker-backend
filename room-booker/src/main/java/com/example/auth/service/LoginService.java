package com.example.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.auth.dto.JwtPayload;
import com.example.auth.dto.TokenResponse;
import com.example.auth.factory.TokenResponseFactory;

@Service
public class LoginService {
    private static final String BEARER = "Bearer";

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AccessTokenService accessTokenService;
    @Autowired
    private RefreshTokenService refreshTokenService;
    @Autowired
    private TokenResponseFactory tokenResponseFactory;

    public TokenResponse login(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(email, password)
        );

        JwtPayload userDetails = (JwtPayload) authentication.getPrincipal();
        long currentTimeMillis = System.currentTimeMillis();

        String accessToken = accessTokenService.generate(
            userDetails.getEmail(), userDetails.getId(), currentTimeMillis
        );
        String refreshToken = refreshTokenService.generate(
            userDetails.getEmail(), userDetails.getId(), currentTimeMillis
        );

        return tokenResponseFactory.create(
            accessToken,
            refreshToken,
            BEARER,
            accessTokenService.getExpirationDate(accessToken)
        );    
    }
}