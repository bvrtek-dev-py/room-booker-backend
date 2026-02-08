package com.example.user.dto.response;

import com.example.user.role.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    @NotNull
    private Long id;
    @NotBlank
    private String username;
    @Email
    private String email;
    @NotNull
    private UserRole role;
}
