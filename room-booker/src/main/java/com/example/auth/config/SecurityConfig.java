package com.example.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.auth.authenticator.Authenticator;
import com.example.auth.filter.JwtAuthFilter;

@Configuration
public class SecurityConfig {
    @Autowired
    private JwtAuthFilter jwtAuthFilter;
    @Autowired
    private Authenticator authenticator;

    @Bean
    @Order(1)
    public SecurityFilterChain publicEndpoints(HttpSecurity http) throws Exception {
        http
            .securityMatcher("/api/v1/auth/**", "/api/v1/users") // Obsługuje tylko te ścieżki
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/auth/**").permitAll() // Zezwól na wszystkie żądania do /api/v1/auth/**
                .requestMatchers(HttpMethod.POST, "/api/v1/users").permitAll() // Zezwól tylko na POST do /api/v1/users
            );
        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain securedEndpoints(HttpSecurity http) throws Exception {
        http
            .securityMatcher("/**") // Obsługuje wszystkie inne żądania
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .anyRequest().authenticated() // Wymaga uwierzytelnienia dla wszystkich innych żądań
            )
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); // Dodaj filtr JWT
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(authenticator)
                .build();
    }

    // @Bean
    // public AuthenticationProvider authenticationProvider() {
    //     return authenticator;
    // }
}