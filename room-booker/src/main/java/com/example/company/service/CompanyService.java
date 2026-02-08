package com.example.company.service;

import java.util.List;

import com.example.company.entity.CompanyAddressEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

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
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;

    private final CompanyEntityFactory companyModelFactory;

    private final CompanyEntityMapper companyModelMapper;

    private final UserGetById userGetById;

    private final CompanyResponseMapperFacade companyResponseMapper;

    private final AddressCreate addressCreate;

    private final CompanyAddressRepository companyAddressRepository;

    public List<CompanyResponse> getAllCompanies() {
        return companyRepository.findAll().stream()
                .map(company -> {
                    var address = companyAddressRepository
                            .findByObjectId(company.getId())
                            .orElse(null);
                    return companyResponseMapper.map(company, address);
                })
                .toList();
    }

    public CompanyResponse getById(@NotNull Long id) {
        CompanyEntity entity = getEntityById(id);
        CompanyAddressEntity address = companyAddressRepository.findByObjectId(entity.getId()).orElse(null);

        return companyResponseMapper.map(entity, address);
    }

    @Transactional
    public CompanyResponse create(@NotNull CompanyCreateRequest company, @NotNull JwtPayload user) {
        UserEntity userEntity = userGetById.execute(user.getId());
        CompanyEntity entity = companyModelMapper.map(company, userEntity);

        existsByName(entity.getName());

        CompanyEntity savedEntity = companyRepository.save(entity);

        AddressEntity address = null;
        if (company.getAddress() != null) {
            address = addressCreate.execute(company.getAddress(), savedEntity.getId());
        }

        return companyResponseMapper.map(savedEntity, address);
    }

    @Transactional
    public CompanyResponse update(@NotNull Long id, @NotNull CompanyUpdateRequest request, @NotNull JwtPayload user) {
        existsByName(request.getName());

        UserEntity userEntity = userGetById.execute(user.getId());
        CompanyEntity existingCompany = getEntityById(id);

        if (!existingCompany.getUser().getId().equals(userEntity.getId())) {
            throw new PermissionDeniedException();
        }

        CompanyEntity companyToUpdate =
                companyModelFactory.make(existingCompany.getId(), request.getName(), userEntity);
        CompanyEntity savedCompany = companyRepository.save(companyToUpdate);

        AddressEntity address = null;
        if (request.getAddress() != null) {
            companyAddressRepository.findByObjectId(id).ifPresent(companyAddressRepository::delete);
            address = addressCreate.execute(request.getAddress(), savedCompany.getId());
        }

        return companyResponseMapper.map(savedCompany, address);
    }

    public void delete(@NotNull Long id, @NotNull JwtPayload user) {
        CompanyEntity entity = companyRepository.findById(id).orElseThrow(ObjectNotFoundException::new);

        if (!entity.getUser().getId().equals(user.getId())) {
            throw new PermissionDeniedException();
        }

        companyRepository.deleteById(id);
    }

    private void existsByName(@NotNull String name) {
        if (companyRepository.existsByName(name)) {
            throw new ObjectAlreadyExistsException();
        }
    }

    private CompanyEntity getEntityById(@NotNull Long id) {
        return companyRepository.findById(id).orElseThrow(ObjectNotFoundException::new);
    }
}
