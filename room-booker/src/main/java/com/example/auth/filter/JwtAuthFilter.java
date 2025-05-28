package com.example.auth.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.auth.dto.JwtPayload;
import com.example.auth.factory.UsernamePasswordAuthenticationTokenFactory;
import com.example.auth.service.AccessTokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    @Autowired
    private AccessTokenService jwtService;
    @Autowired
    private UsernamePasswordAuthenticationTokenFactory authenticationTokenFactory;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String token = jwtService.extractTokenFromHeader(request);

        if (token == null) {
            chain.doFilter(request, response);
            return;
        }

        JwtPayload payload = jwtService.extractPayload(token);
        String email = payload.getEmail();

        if (email == null || SecurityContextHolder.getContext().getAuthentication() != null) {
            chain.doFilter(request, response);
            return;
        }

        if (!jwtService.isTokenValid(token)) {
            chain.doFilter(request, response);
            return;
       }

        UsernamePasswordAuthenticationToken authenticationToken = authenticationTokenFactory.make(payload, null);

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        chain.doFilter(request, response);
    }
}