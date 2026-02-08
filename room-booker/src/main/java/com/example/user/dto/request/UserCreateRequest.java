package com.example.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateRequest extends UserBaseRequest {
    @NotBlank(message = "Password is required")
    protected String password;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    protected String email;

    public UserCreateRequest(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public UserCreateRequest with(String password, String email, String username) {
        UserCreateRequest newRequest = new UserCreateRequest();

        newRequest.password = (password != null) ? password : this.password;
        newRequest.email = (email != null) ? email : this.email;
        newRequest.username = (username != null) ? username : this.username;

        return newRequest;
    }
}
