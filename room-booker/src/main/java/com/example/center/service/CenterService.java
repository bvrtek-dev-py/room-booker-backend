package com.example.center.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class CenterService {
    @Autowired
    private CenterRepository centerRepository;

    @Autowired
    private CenterEntityMapper centerEntityMapper;

    @Autowired
    private CenterResponseMapperFacade centerResponseMapperFacade;

    @Autowired
    private CompanyGetIfUserIsOwner companyGetIfUserIsOwner;

    @Autowired
    private CompanyGetById companyGetById;

    @Autowired
    private CenterGetById centerGetById;

    @Autowired
    private AddressCreate addressCreate;

    @Autowired
    private CenterAddressRepository centerAddressRepository;

    @Autowired
    private AddressUpdate addressUpdate;

    public CenterResponse create(CenterCreateRequest request, Long companyId, JwtPayload user) {
        CompanyEntity company = companyGetIfUserIsOwner.execute(companyId, user.getId());
        this.existsByName(request.getName());

        CenterEntity entity = centerEntityMapper.map(request, company);
        CenterEntity persistedEntity = centerRepository.save(entity);
        AddressEntity address = addressCreate.execute(request.getAddress(), persistedEntity.getId());

        return centerResponseMapperFacade.map(persistedEntity, address);
    }

    public CenterResponse update(Long id, CenterUpdateRequest request, Long userId) {
        CenterEntity existingEntity = centerGetById.execute(id);

        throwIfNotCompanyOwner(existingEntity, userId);
        this.existsByName(request.name());

        CenterEntity entity =
                existingEntity.with(Optional.of(request.name()), Optional.of(request.description()), Optional.empty());
        CenterEntity persistedEntity = centerRepository.save(entity);
        AddressEntity address = addressUpdate.execute(request.address(), persistedEntity.getId());

        return centerResponseMapperFacade.map(persistedEntity, address);
    }

    public List<CenterResponse> getAllByCompanyId(Long companyId) {
        return centerRepository.findByCompany(companyGetById.execute(companyId)).stream()
                .map(center -> {
                    var address = centerAddressRepository.findByObjectId(center.getId()).orElse(null);
                    return centerResponseMapperFacade.map(center, address);
                })
                .toList();
    }

    public CenterResponse getById(Long id) {
        var center = centerGetById.execute(id);
        var address = centerAddressRepository.findByObjectId(center.getId()).orElse(null);
        return centerResponseMapperFacade.map(center, address);
    }

    public void delete(Long id, Long userId) {
        CenterEntity entity = centerGetById.execute(id);

        throwIfNotCompanyOwner(entity, userId);

        centerRepository.deleteById(id);
    }

    private void existsByName(String name) {
        if (centerRepository.existsByName(name)) {
            throw new ObjectAlreadyExistsException();
        }
    }

    private void throwIfNotCompanyOwner(CenterEntity entity, Long userId) {
        if (!entity.getCompany().getUser().getId().equals(userId)) {
            throw new PermissionDeniedException();
        }
    }
}
