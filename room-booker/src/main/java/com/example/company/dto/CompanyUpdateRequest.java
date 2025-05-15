package com.example.company.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CompanyUpdateRequest(
    @NotNull
    @Size(min = 1, max = 255)
    String name
) {}