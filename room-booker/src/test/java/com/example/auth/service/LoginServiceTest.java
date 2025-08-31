package com.example.auth.service;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.example.auth.dto.JwtPayload;
import com.example.auth.dto.TokenResponse;
import com.example.auth.factory.TokenResponseFactory;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private AccessTokenService accessTokenService;

    @Mock
    private RefreshTokenService refreshTokenService;

    @Mock
    private TokenResponseFactory tokenResponseFactory;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private LoginService loginService;

    @Test
    void login_shouldReturnTokenResponse() {
        // given
        String email = "user@example.com";
        String password = "password";
        Long userId = 1L;
        long now = System.currentTimeMillis();
        JwtPayload userDetails = new JwtPayload(userId, email);
        String accessToken = "access.token";
        String refreshToken = "refresh.token";
        String bearer = "Bearer";
        TokenResponse expectedResponse = mock(TokenResponse.class); // albo mock(TokenResponse.class)

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(accessTokenService.generate(eq(email), eq(userId), anyLong())).thenReturn(accessToken);
        when(refreshTokenService.generate(eq(email), eq(userId), anyLong())).thenReturn(refreshToken);
        when(accessTokenService.getExpirationDate(eq(accessToken))).thenReturn(new java.util.Date(now + 10000));
        when(tokenResponseFactory.create(eq(accessToken), eq(refreshToken), eq(bearer), any()))
                .thenReturn(expectedResponse);

        // when
        TokenResponse response = loginService.login(email, password);

        // then
        assertThat(response).isEqualTo(expectedResponse);

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(authentication).getPrincipal();
        verify(accessTokenService).generate(eq(email), eq(userId), anyLong());
        verify(refreshTokenService).generate(eq(email), eq(userId), anyLong());
        verify(tokenResponseFactory).create(eq(accessToken), eq(refreshToken), eq(bearer), any());
    }
}
