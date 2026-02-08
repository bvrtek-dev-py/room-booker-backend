package com.example.user.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserUpdateRequest extends UserBaseRequest {
    public UserUpdateRequest(String username) {
        this.username = username;
    }
}
