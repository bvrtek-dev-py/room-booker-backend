package com.example.company.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CompanyCreateRequest(
    @NotNull
    @Size(min = 1, max = 255)
    String name
) {}