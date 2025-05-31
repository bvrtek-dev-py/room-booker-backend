package com.example.company.factory;

import org.springframework.stereotype.Component;

import com.example.company.entity.CompanyEntity;
import com.example.user.entity.UserEntity;

@Component
public class CompanyEntityFactory {
    public CompanyEntity make(Long id, String name, UserEntity user) {
        return new CompanyEntity(id, name, user);
    }
}
