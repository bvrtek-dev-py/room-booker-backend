package com.example.center.dto.response;

import com.example.address.dto.AddressResponse;
import com.example.company.dto.response.CompanyResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CenterResponse {
    @NotNull
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull
    private AddressResponse address;
    @NotNull
    private CompanyResponse company;
}
