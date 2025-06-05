package com.example.company.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.auth.dto.JwtPayload;
import com.example.common.exception.ObjectAlreadyExistsException;
import com.example.common.exception.ObjectNotFoundException;
import com.example.common.exception.PermissionDeniedException;
import com.example.company.dto.request.CompanyCreateRequest;
import com.example.company.dto.request.CompanyUpdateRequest;
import com.example.company.dto.response.CompanyResponse;
import com.example.company.entity.CompanyEntity;
import com.example.company.factory.CompanyEntityFactory;
import com.example.company.mapper.CompanyEntityMapper;
import com.example.company.mapper.CompanyResponseMapperFacade;
import com.example.company.repository.CompanyRepository;
import com.example.user.entity.UserEntity;
import com.example.user.use_case.UserGetById;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyEntityFactory companyModelFactory;

    @Autowired
    private CompanyEntityMapper companyModelMapper;

    @Autowired
    private UserGetById userGetById;

    @Autowired
    private CompanyResponseMapperFacade companyResponseMapper;

    public List<CompanyResponse> getAllCompanies() {
        return companyRepository.findAll().stream()
                .map(companyResponseMapper::map)
                .toList();
    }

    public CompanyResponse getById(Long id) {
        CompanyEntity entity = getEntityById(id);

        return companyResponseMapper.map(entity);
    }

    public CompanyResponse create(CompanyCreateRequest company, JwtPayload user) {
        UserEntity userEntity = userGetById.execute(user.getId());
        CompanyEntity entity = companyModelMapper.map(company, userEntity);

        this.existsByName(entity.getName());

        CompanyEntity savedEntity = companyRepository.save(entity);

        return companyResponseMapper.map(savedEntity);
    }

    public CompanyResponse update(Long id, CompanyUpdateRequest request, JwtPayload user) {
        this.existsByName(request.name());

        UserEntity userEntity = userGetById.execute(user.getId());
        CompanyEntity existingCompany = getEntityById(id);

        if (!existingCompany.getUser().getId().equals(userEntity.getId())) {
            throw new PermissionDeniedException();
        }

        CompanyEntity companyToUpdate = companyModelFactory.make(existingCompany.getId(), request.name(), userEntity);
        CompanyEntity savedCompany = companyRepository.save(companyToUpdate);

        return companyResponseMapper.map(savedCompany);
    }

    public void delete(Long id, JwtPayload user) {
        CompanyEntity entity = companyRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException());

        if (!entity.getUser().getId().equals(user.getId())) {
            throw new PermissionDeniedException();
        }

        companyRepository.deleteById(id);
    }

    private void existsByName(String name) {
        if (companyRepository.existsByName(name)) {
            throw new ObjectAlreadyExistsException();
        }
    }

    private CompanyEntity getEntityById(Long id) {
        return companyRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException());
    }
}
