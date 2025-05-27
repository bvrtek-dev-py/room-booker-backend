package com.example.auth.dto;

public class JwtPayload {
    private Long id;
    private String email;

    public JwtPayload(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}