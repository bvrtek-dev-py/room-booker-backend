package com.example.company.factory;

import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

import com.example.company.entity.CompanyEntity;
import com.example.user.entity.UserEntity;

@Component
public class CompanyEntityFactory {
    public CompanyEntity make(@NotNull Long id, @NotNull String name, @NotNull UserEntity user) {
        return new CompanyEntity(id, name, user);
    }
}
