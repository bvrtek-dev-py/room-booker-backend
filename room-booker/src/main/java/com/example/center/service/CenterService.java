package com.example.center.service;

import java.util.List;

import com.example.center.entity.CenterAddressEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import com.example.address.entity.AddressEntity;
import com.example.address.use_case.AddressCreate;
import com.example.address.use_case.AddressUpdate;
import com.example.auth.dto.JwtPayload;
import com.example.center.dto.request.CenterCreateRequest;
import com.example.center.dto.request.CenterUpdateRequest;
import com.example.center.dto.response.CenterResponse;
import com.example.center.entity.CenterEntity;
import com.example.center.mapper.CenterEntityMapper;
import com.example.center.mapper.CenterResponseMapperFacade;
import com.example.center.repository.CenterAddressRepository;
import com.example.center.repository.CenterRepository;
import com.example.center.use_case.CenterGetById;
import com.example.common.exception.ObjectAlreadyExistsException;
import com.example.common.exception.PermissionDeniedException;
import com.example.company.entity.CompanyEntity;
import com.example.company.use_case.CompanyGetById;
import com.example.company.use_case.CompanyGetIfUserIsOwner;

@Service
@RequiredArgsConstructor
public class CenterService {
    private final CenterRepository centerRepository;

    private final CenterEntityMapper centerEntityMapper;

    private final CenterResponseMapperFacade centerResponseMapperFacade;

    private final CompanyGetIfUserIsOwner companyGetIfUserIsOwner;

    private final CompanyGetById companyGetById;

    private final CenterGetById centerGetById;

    private final AddressCreate addressCreate;

    private final CenterAddressRepository centerAddressRepository;

    private final AddressUpdate addressUpdate;

    public CenterResponse create(@NotNull CenterCreateRequest request, @NotNull Long companyId, @NotNull JwtPayload user) {
        CompanyEntity company = companyGetIfUserIsOwner.execute(companyId, user.getId());
        existsByName(request.getName());

        CenterEntity entity = centerEntityMapper.map(request, company);
        CenterEntity persistedEntity = centerRepository.save(entity);
        AddressEntity address = addressCreate.execute(request.getAddress(), persistedEntity.getId());

        return centerResponseMapperFacade.map(persistedEntity, address);
    }

    public CenterResponse update(@NotNull Long id, @NotNull CenterUpdateRequest request, @NotNull Long userId) {
        CenterEntity existingEntity = centerGetById.execute(id);

        throwIfNotCompanyOwner(existingEntity, userId);
        existsByName(request.getName());

        CenterEntity entity = existingEntity.with(null, request.getName(), request.getDescription(), null);
        CenterEntity persistedEntity = centerRepository.save(entity);
        AddressEntity address = addressUpdate.execute(request.getAddress(), persistedEntity.getId());

        return centerResponseMapperFacade.map(persistedEntity, address);
    }

    public List<CenterResponse> getAllByCompanyId(@NotNull Long companyId) {
        return centerRepository.findByCompany(companyGetById.execute(companyId)).stream()
                .map(center -> {
                    var address = centerAddressRepository
                            .findByObjectId(center.getId())
                            .orElse(null);
                    return centerResponseMapperFacade.map(center, address);
                })
                .toList();
    }

    public CenterResponse getById(@NotNull Long id) {
        CenterEntity center = centerGetById.execute(id);
        CenterAddressEntity address = centerAddressRepository.findByObjectId(center.getId()).orElse(null);

        return centerResponseMapperFacade.map(center, address);
    }

    public void delete(@NotNull Long id, @NotNull Long userId) {
        CenterEntity entity = centerGetById.execute(id);

        throwIfNotCompanyOwner(entity, userId);

        centerRepository.deleteById(id);
    }

    private void existsByName(@NotNull String name) {
        if (centerRepository.existsByName(name)) {
            throw new ObjectAlreadyExistsException();
        }
    }

    private void throwIfNotCompanyOwner(@NotNull CenterEntity entity, @NotNull Long userId) {
        if (!entity.getCompany().getUser().getId().equals(userId)) {
            throw new PermissionDeniedException();
        }
    }
}
