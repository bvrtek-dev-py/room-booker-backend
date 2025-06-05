package com.example.user.dto.response;

import com.example.user.role.UserRole;

public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private UserRole role;

    public UserResponse(Long id, String username, String email, UserRole role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public UserRole getRole() {
        return role;
    }
}
