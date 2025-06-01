package com.example.center.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.auth.dto.JwtPayload;
import com.example.center.dto.request.CenterCreateRequest;
import com.example.center.dto.request.CenterUpdateRequest;
import com.example.center.dto.response.CenterResponse;
import com.example.center.entity.CenterEntity;
import com.example.center.mapper.CenterEntityMapper;
import com.example.center.mapper.CenterResponseMapperFacade;
import com.example.center.repository.CenterRepository;
import com.example.common.exception.ObjectAlreadyExistsException;
import com.example.common.exception.ObjectNotFoundException;
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
    private CenterResponseMapperFacade centerResponseMapper;
    @Autowired
    private CompanyGetIfUserIsOwner companyGetIfUserIsOwner;
    @Autowired
    private CompanyGetById companyGetById;

    public CenterResponse create(CenterCreateRequest request, Long companyId, JwtPayload user) {
        CompanyEntity company = companyGetIfUserIsOwner.execute(companyId, user.getId());
        
        this.existsByName(request.name());

        CenterEntity entity = centerEntityMapper.map(request, company);
        CenterEntity persistedEntity = centerRepository.save(entity);

        return centerResponseMapper.map(persistedEntity);
    }

    public CenterResponse update(Long id, CenterUpdateRequest request, Long userId) {
        CenterEntity existingEntity = getEntityById(id);
        
        throwIfNotCompanyOwner(existingEntity, userId);
        this.existsByName(request.name());

        CenterEntity entity = existingEntity.with(
            Optional.of(request.name()), Optional.of(request.description()), Optional.empty()
        );
        CenterEntity persistedEntity = centerRepository.save(entity);

        return centerResponseMapper.map(persistedEntity);
    }

    public List<CenterResponse> getAllByCompanyId(Long companyId) {
        return centerRepository.findByCompany(companyGetById.execute(companyId)).stream()
                .map(centerResponseMapper::map)
                .toList();
    }

    public CenterResponse getById(Long id) {
        return centerResponseMapper.map(getEntityById(id));
    }

    public void delete(Long id, Long userId) {
        CenterEntity entity = getEntityById(id);

        throwIfNotCompanyOwner(entity, userId);

        centerRepository.deleteById(id);
    }

    private void existsByName(String name) {
        if (centerRepository.existsByName(name)) {
            throw new ObjectAlreadyExistsException();
        }
    }

    private CenterEntity getEntityById(Long id) {
        return centerRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException());
    }

    private void throwIfNotCompanyOwner(CenterEntity entity, Long userId) {
        if (!entity.getCompany().getUser().getId().equals(userId)) {
            throw new PermissionDeniedException();
        }
    }
}