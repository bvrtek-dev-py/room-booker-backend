package com.example.company.use_case;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.common.exception.ObjectNotFoundException;
import com.example.company.entity.CompanyEntity;
import com.example.company.repository.CompanyRepository;

@Component
public class CompanyGetById {
    @Autowired
    private CompanyRepository companyRepository;

    public CompanyEntity execute(Long id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException());
    }
}
