package com.example.company.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.address.entity.AddressEntity;
import com.example.address.use_case.AddressCreate;
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
import com.example.company.repository.CompanyAddressRepository;
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

    @Autowired
    private AddressCreate addressCreate;

    @Autowired
    private CompanyAddressRepository companyAddressRepository;

    public List<CompanyResponse> getAllCompanies() {
        return companyRepository.findAll().stream()
                .map(company -> {
                    var address = companyAddressRepository.findByObjectId(company.getId()).orElse(null);
                    return companyResponseMapper.map(company, address);
                })
                .toList();
    }

    public CompanyResponse getById(Long id) {
        CompanyEntity entity = getEntityById(id);
        var address = companyAddressRepository.findByObjectId(entity.getId()).orElse(null);
        return companyResponseMapper.map(entity, address);
    }

    @Transactional
    public CompanyResponse create(CompanyCreateRequest company, JwtPayload user) {
        UserEntity userEntity = userGetById.execute(user.getId());
        CompanyEntity entity = companyModelMapper.map(company, userEntity);

        this.existsByName(entity.getName());

        CompanyEntity savedEntity = companyRepository.save(entity);

        AddressEntity address = null;
        if (company.getAddress() != null) {
            address = addressCreate.execute(company.getAddress(), savedEntity.getId());
        }

        return companyResponseMapper.map(savedEntity, address);
    }

    @Transactional
    public CompanyResponse update(Long id, CompanyUpdateRequest request, JwtPayload user) {
        this.existsByName(request.getName());

        UserEntity userEntity = userGetById.execute(user.getId());
        CompanyEntity existingCompany = getEntityById(id);

        if (!existingCompany.getUser().getId().equals(userEntity.getId())) {
            throw new PermissionDeniedException();
        }

        CompanyEntity companyToUpdate = companyModelFactory.make(existingCompany.getId(), request.getName(), userEntity);
        CompanyEntity savedCompany = companyRepository.save(companyToUpdate);

        AddressEntity address = null;
        if (request.getAddress() != null) {
            companyAddressRepository.findByObjectId(id).ifPresent(companyAddressRepository::delete);
            address = addressCreate.execute(request.getAddress(), savedCompany.getId());
        }

        return companyResponseMapper.map(savedCompany, address);
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
