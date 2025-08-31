package com.example.auth.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.auth.dto.JwtPayload;
import com.example.auth.factory.UsernamePasswordAuthenticationTokenFactory;
import com.example.auth.service.AccessTokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ExtendWith(MockitoExtension.class)
class JwtAuthFilterTest {
    @Mock
    private AccessTokenService jwtService;
    @Mock
    private UsernamePasswordAuthenticationTokenFactory authenticationTokenFactory;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain filterChain;
    @InjectMocks
    private JwtAuthFilter filter;

    @Test
    void shouldSetAuthenticationAndContinueChain_whenTokenIsValid() throws Exception {
        // given
        SecurityContextHolder.clearContext();
        String token = "valid.jwt.token";
        JwtPayload payload = new JwtPayload(1L, "user@example.com");
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(payload, null);
        when(jwtService.extractTokenFromHeader(request)).thenReturn(token);
        when(jwtService.extractPayload(token)).thenReturn(payload);
        when(jwtService.isTokenValid(token)).thenReturn(true);
        when(authenticationTokenFactory.make(payload, null)).thenReturn(authToken);

        // when
        filter.doFilterInternal(request, response, filterChain);

        // then
        verify(jwtService).extractTokenFromHeader(request);
        verify(jwtService).extractPayload(token);
        verify(jwtService).isTokenValid(token);
        verify(authenticationTokenFactory).make(payload, null);
        verify(filterChain).doFilter(request, response);
        assertEquals(authToken, SecurityContextHolder.getContext().getAuthentication());
    }
}
