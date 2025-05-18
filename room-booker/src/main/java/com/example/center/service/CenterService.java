package com.example.center.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.center.dto.request.CenterCreateRequest;
import com.example.center.dto.request.CenterUpdateRequest;
import com.example.center.dto.response.CenterResponse;
import com.example.center.entity.CenterEntity;
import com.example.center.mapper.CenterEntityMapper;
import com.example.center.mapper.CenterResponseMapper;
import com.example.center.repository.CenterRepository;
import com.example.common.exception.ObjectAlreadyExistsException;
import com.example.common.exception.ObjectNotFoundException;

@Service
public class CenterService {
    @Autowired
    private CenterRepository centerRepository;
    @Autowired
    private CenterEntityMapper centerEntityMapper;
    @Autowired
    private CenterResponseMapper centerResponseMapper;

    public CenterResponse create(CenterCreateRequest request) {
        this.existsByName(request.name());

        CenterEntity entity = centerEntityMapper.map(request);
        CenterEntity persistedEntity = centerRepository.save(entity);

        return centerResponseMapper.map(persistedEntity);
    }

    public CenterResponse update(Long id, CenterUpdateRequest request) {
        centerRepository.findById(id)
            .orElseThrow(() -> new ObjectNotFoundException());
        
        this.existsByName(request.name());

        CenterEntity entity = centerEntityMapper.map(request, id);
        CenterEntity persistedEntity = centerRepository.save(entity);

        return centerResponseMapper.map(persistedEntity);
    }

    public List<CenterResponse> getAll() {
        return centerRepository.findAll().stream()
                .map(centerResponseMapper::map)
                .toList();
    }

    public CenterResponse getById(Long id) {
        CenterEntity entity = centerRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException());

        return centerResponseMapper.map(entity);
    }

    public void delete(Long id) {
        this.getById(id);

        centerRepository.deleteById(id);
    }

    private void existsByName(String name) {
        if (centerRepository.existsByName(name)) {
            throw new ObjectAlreadyExistsException();
        }
    }
}