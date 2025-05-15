package com.example.company.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.common.exception.ObjectAlreadyExistsException;
import com.example.common.exception.ObjectNotFoundException;
import com.example.company.dto.CompanyCreateRequest;
import com.example.company.dto.CompanyUpdateRequest;
import com.example.company.entity.CompanyEntity;
import com.example.company.factory.CompanyEntityFactory;
import com.example.company.mapper.CompanyEntityMapper;
import com.example.company.repository.CompanyRepository;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private CompanyEntityFactory companyModelFactory;
    @Autowired
    private CompanyEntityMapper companyModelMapper;

    public List<CompanyEntity> getAllCompanies() {
        return companyRepository.findAll();
    }

    public CompanyEntity getById(Long id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException());
    }

    public CompanyEntity create(CompanyCreateRequest company) {
        CompanyEntity entity = companyModelMapper.toEntity(company);

        this.existsByName(entity.getName());
        
        return companyRepository.save(entity);
    }

    public CompanyEntity update(Long id, CompanyUpdateRequest request) {
        this.existsByName(request.name());

        return companyRepository.findById(id).map(company -> {
            company = companyModelFactory.make(company.getId(), request.name());
            return companyRepository.save(company);
        }).orElseThrow(() -> new ObjectNotFoundException());
    }

    public void delete(Long id) {
        companyRepository.deleteById(id);
    }

    private void  existsByName(String name) {
        if (companyRepository.existsByName(name)) {
            throw new ObjectAlreadyExistsException();
        }
    }
}