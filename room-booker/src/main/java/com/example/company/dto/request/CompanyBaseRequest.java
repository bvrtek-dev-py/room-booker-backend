package com.example.company.dto.request;

import com.example.address.dto.AddressCreateRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyBaseRequest {
    @NotNull
    @Size(min = 1, max = 255)
    private String name;

    private AddressCreateRequest address;
}
