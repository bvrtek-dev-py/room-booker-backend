package com.example.company.use_case;

import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import com.example.common.exception.ObjectNotFoundException;
import com.example.company.entity.CompanyEntity;
import com.example.company.repository.CompanyRepository;

@Component
@RequiredArgsConstructor
public class CompanyGetById {
    private final CompanyRepository companyRepository;

    public CompanyEntity execute(@NotNull Long id) {
        return companyRepository.findById(id).orElseThrow(ObjectNotFoundException::new);
    }
}
