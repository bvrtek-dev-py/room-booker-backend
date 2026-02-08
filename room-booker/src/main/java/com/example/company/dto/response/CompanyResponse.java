package com.example.company.dto.response;

import com.example.address.dto.AddressResponse;
import com.example.user.dto.response.UserResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyResponse {
    @NotNull
    private Long id;
    @NotBlank
    private String name;
    @NotNull
    private UserResponse user;
    @NotNull
    private AddressResponse address;
}
