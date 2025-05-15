package com.example.company.factory;

import org.springframework.stereotype.Component;

import com.example.company.entity.CompanyEntity;

@Component
public class CompanyEntityFactory {
    public CompanyEntity make(Long id, String name) {
        return new CompanyEntity(id, name);
    }
}
